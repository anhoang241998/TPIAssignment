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
import com.annguyenhoang.tpiassignment.databinding.ItemTouristLoadMoreBinding
import com.annguyenhoang.tpiassignment.views.tourist_list.models.Tourist
import com.annguyenhoang.tpiassignment.views.tourist_list.models.TouristRecyclerViewType

class TouristsAdapter : ListAdapter<TouristRecyclerViewType, RecyclerView.ViewHolder>(DIFF_UTIL) {

    private var onItemClicked: ((Tourist) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TOURIST_ITEM_VIEW_TYPE) {
            TouristsViewHolder(
                ItemTouristBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            LoadMoreViewHolder(
                ItemTouristLoadMoreBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (currentList[position].viewType == TOURIST_ITEM_VIEW_TYPE) {
            val currTourist = getItem(position) as Tourist
            (holder as TouristsViewHolder).bind(currTourist)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType
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

    inner class LoadMoreViewHolder(
        binding: ItemTouristLoadMoreBinding
    ) : RecyclerView.ViewHolder(binding.root)

    fun setOnItemClicked(onItemClicked: (Tourist) -> Unit) {
        this.onItemClicked = onItemClicked
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<TouristRecyclerViewType>() {
            override fun areItemsTheSame(oldItem: TouristRecyclerViewType, newItem: TouristRecyclerViewType): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TouristRecyclerViewType,
                newItem: TouristRecyclerViewType
            ): Boolean {
                return oldItem.equals(newItem)
            }
        }

        const val TOURIST_ITEM_VIEW_TYPE = 0
        const val LOAD_MORE_ITEM_VIEW_TYPE = 1
    }

}