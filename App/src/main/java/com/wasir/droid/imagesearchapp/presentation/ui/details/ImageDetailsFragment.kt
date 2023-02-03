package com.wasir.droid.imagesearchapp.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.request.Disposable
import coil.request.ImageRequest
import com.wasir.droid.core.data.model.ImageData
import com.wasir.droid.imagesearchapp.R
import com.wasir.droid.imagesearchapp.databinding.FragmentDetailsBinding
import com.wasir.droid.imagesearchapp.presentation.util.prettyText
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ImageDetailsFragment : Fragment() {

    @Inject
    lateinit var imageLoader: ImageLoader
    private var _binding: FragmentDetailsBinding? = null
    private val args: ImageDetailsFragmentArgs by navArgs()
    private var disposable: Disposable? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var imageData: ImageData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageData = args.imgData
        val request = ImageRequest.Builder(requireContext())
            .data(imageData?.largeImageURL)
            .crossfade(true)
            .target(
                onStart = {
                    binding.progressBar.show()
                },
                onSuccess = {
                    binding.ivImage.setImageDrawable(it)
                    binding.progressBar.hide()
                },
                onError = {
                    binding.ivImage.setImageDrawable(it)
                    binding.progressBar.hide()
                }
            )
            .build()
        disposable = imageLoader.enqueue(request)
        bindDataOnUI()
    }

    private fun bindDataOnUI() {
        binding.tvName.text = String.format(
            getString(
                R.string.formated_name, imageData?.user
            )
        )
        binding.tvLikes.prettyText(imageData?.likes)
        binding.tvComments.prettyText(imageData?.comments)
        binding.tvDownloads.prettyText(imageData?.downloads)
        binding.tvTags.text = String.format(
            getString(
                R.string.formated_tags, imageData?.tags
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable?.dispose()
        _binding = null
    }
}