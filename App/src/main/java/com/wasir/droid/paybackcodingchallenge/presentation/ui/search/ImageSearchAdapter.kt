package com.wasir.droid.paybackcodingchallenge.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.wasir.droid.core.data.model.ImageData
import com.wasir.droid.paybackcodingchallenge.R
import com.wasir.droid.paybackcodingchallenge.databinding.RecyclerItemLayoutBinding

class ImageSearchAdapter(
    private val items: MutableList<ImageData>,
    private val itemClick: (ImageData) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecyclerItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewHolder: ImageViewHolder = holder as ImageViewHolder
        viewHolder.bindView(items[position])

    }

    private fun add(imgData: ImageData) {
        items.add(imgData)
        notifyItemInserted(items.size - 1)
    }

    /*Remove old data if any and create new list*/
    fun submitNewList(images: List<ImageData>, removeOldData: Boolean) {
        if (removeOldData) {
            val size = items.size
            items.clear()
            notifyItemRangeRemoved(0, size)
        }
        for (img in images) {
            add(img)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ImageViewHolder(
        private val binding: RecyclerItemLayoutBinding
    ) : ViewHolder(binding.root) {
        fun bindView(item: ImageData) {
            with(item) {
                binding.tvName.text = String.format(
                    binding.root.context.getString(
                        R.string.formated_name, user
                    )
                )
                binding.tvTags.text = String.format(
                    binding.root.context.getString(
                        R.string.formated_tags, tags
                    )
                )
                binding.ivImage.load(previewURL)
                binding.root.setOnClickListener { itemClick(this) }
            }
        }
    }
}