package com.wasir.droid.paybackcodingchallenge.presentation.ui.search

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.google.android.material.snackbar.Snackbar
import com.wasir.droid.core.data.model.ImageData
import com.wasir.droid.paybackcodingchallenge.R
import com.wasir.droid.paybackcodingchallenge.databinding.FragmentSearchBinding
import com.wasir.droid.paybackcodingchallenge.presentation.util.NetworkConnectivity
import com.wasir.droid.paybackcodingchallenge.presentation.util.getQueryTextChangeStateFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

private const val SELECTED_IMAGE_ID = "selected_img_id"

@AndroidEntryPoint
open class ImageSearchFragment : Fragment(), MenuProvider {
    val searchViewModel: ImageSearchViewModel by viewModels()
    private var _binding: FragmentSearchBinding? = null
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var imageSearchAdapter: ImageSearchAdapter
    private var confirmationDialog: AlertDialog? = null

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var avoidRedundantNetworkCall: Boolean = false
    private var selectedImageId: Int = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState?.let {
            /* checking is dialog needs to reopen after configuration change */
            savedInstanceState.getInt(SELECTED_IMAGE_ID).also { id ->
                if (id > 0) clickOnImage(searchViewModel.searchResult.find { it.id == id } as ImageData)
            }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        observeSearchData()
        setupAdapter()
    }

    override fun onPause() {
        super.onPause()
        /*clearing active/visible dialogs during configuration change or user going background*/
        confirmationDialog?.dismiss()
    }

    override fun onResume() {
        super.onResume()
        avoidRedundantNetworkCall = searchViewModel.searchResult.size > 0
    }

    /* observer to update UI*/
    private fun observeSearchData() {
        searchViewModel.getUiState().observe(viewLifecycleOwner) { it ->
            when (it) {
                is ImageSearchViewModel.UiState.Loading -> {
                    binding.progressBar.show()
                }
                is ImageSearchViewModel.UiState.Success -> {
                    binding.progressBar.hide()
                    it.data?.let {
                        imageSearchAdapter.submitNewList(
                            it,
                            searchViewModel.removePreviousData
                        )
                        searchViewModel.removePreviousData = false
                        if (it.isEmpty()) {
                            binding.tvNoData.visibility = View.VISIBLE
                        } else {
                            binding.tvNoData.visibility = View.GONE
                        }
                    }
                }
                is ImageSearchViewModel.UiState.Error -> {
                    binding.progressBar.hide()
                    Snackbar.make(
                        binding.root,
                        it.message ?: getString(R.string.error_message),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupAdapter() {
        gridLayoutManager =
            GridLayoutManager(requireContext(), 2)
        imageSearchAdapter =
            ImageSearchAdapter(searchViewModel.searchResult) { imageData ->
                clickOnImage(
                    imageData
                )
            }
        binding.imageRV.apply {
            layoutManager = gridLayoutManager
            adapter = imageSearchAdapter
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun clickOnImage(imageData: ImageData) {
        selectedImageId = imageData.id
        val mBuilder = AlertDialog.Builder(requireActivity())
        mBuilder.setMessage(getString(R.string.confirmation_dialog_msg))
            .setPositiveButton(
                getString(R.string.yes)
            ) { _, _ ->
                val action =
                    ImageSearchFragmentDirections.navToDetailsImageFragment(
                        imgData = imageData
                    )
                findNavController().navigate(action)
            }
            .setNegativeButton(
                getString(R.string.no)
            ) { dialog, _ -> dialog.dismiss() }

        confirmationDialog = mBuilder.create()
        confirmationDialog?.show()
        confirmationDialog?.setOnDismissListener {
            selectedImageId = -1
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_IMAGE_ID, selectedImageId)
    }

    /* Handle search view. if user keep typing on search bar,
    it will call request server for images every 500 mills.*/
    @OptIn(FlowPreview::class)
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.isIconified = false
        searchItem.expandActionView()
        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchView.getQueryTextChangeStateFlow()
                    .debounce(500)
                    .distinctUntilChanged()
                    .filter { query ->
                        return@filter query.isNotEmpty()
                    }
                    .flowOn(Dispatchers.Main).collect { query ->
                        requestOnQueryChange(query)
                    }
            }

        }
        searchView.setQuery(searchViewModel.searchQuery, true)
    }

    /*search images from server. avoid redundant server call in case of configuration change.
     Search from local database if there is no internet connection. For local database search, we are not supporting pagination.*/
    private fun requestOnQueryChange(query: String) {
        if (avoidRedundantNetworkCall) {
            avoidRedundantNetworkCall = false
            return
        }
        searchViewModel.searchQuery = query
        searchViewModel.removePreviousData = true

        if (networkConnectivity.isNetworkConnected()) {
            binding.imageRV.addOnScrollListener(onScrollListener)
            searchViewModel.searchImages()
        } else {
            binding.imageRV.removeOnScrollListener(onScrollListener)
            searchViewModel.searchImageOffline()
        }


    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        // Handle the menu selection
        return true
    }

    /*listen recycler view scrolling state for pagination purpose*/
    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy == 0) return
            val lastVisibleIndex: Int = gridLayoutManager.findLastVisibleItemPosition()
            val bottomReached: Boolean =
                lastVisibleIndex >= imageSearchAdapter.itemCount - 5
            if (bottomReached) {
                searchViewModel.searchImages()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}