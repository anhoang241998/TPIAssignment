package com.annguyenhoang.tpiassignment.views.tourist_list.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.annguyenhoang.tpiassignment.R
import com.annguyenhoang.tpiassignment.databinding.ItemTouristBinding
import com.annguyenhoang.tpiassignment.views.tourist_list.models.Tourist

class TouristsAdapter : ListAdapter<Tourist, TouristsAdapter.TouristsViewHolder>(DIFF_UTIL) {

    private var onItemClicked: ((Tourist) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TouristsViewHolder {
        return TouristsViewHolder(
            ItemTouristBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TouristsViewHolder, position: Int) {
        val currTourist = getItem(position)
        holder.bind(currTourist)
    }

    inner class TouristsViewHolder(
        private val binding: ItemTouristBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currTourist: Tourist) {
            binding.tvTouristTitle.text = currTourist.touristTitle
            binding.tvTouristDescription.text = currTourist.touristDescription
            if (currTourist.imageUrl.isNotEmpty()) {
                binding.imgError.visibility = View.INVISIBLE
                binding.imgTourist.visibility = View.VISIBLE
                binding.imgTourist.load(currTourist.imageUrl) {
                    crossfade(true)
                    placeholder(R.drawable.img_loading)
                }
            } else {
                binding.imgError.visibility = View.VISIBLE
                binding.imgTourist.visibility = View.INVISIBLE
            }
            binding.root.setOnClickListener {
                onItemClicked?.invoke(currTourist)
            }
        }
    }

    fun setOnItemClicked(onItemClicked: (Tourist) -> Unit) {
        this.onItemClicked = onItemClicked
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Tourist>() {
            override fun areItemsTheSame(oldItem: Tourist, newItem: Tourist): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tourist, newItem: Tourist): Boolean {
                return oldItem == newItem
            }
        }
    }

}