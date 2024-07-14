package com.annguyenhoang.tpiassignment.views.tourist_list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.annguyenhoang.tpiassignment.databinding.ItemLanguageBinding
import com.annguyenhoang.tpiassignment.views.tourist_list.models.AppLanguage

class LanguageAdapter : ListAdapter<AppLanguage, LanguageAdapter.LanguageViewHolder>(DIFF_UTIL) {

    private var onItemClicked: ((AppLanguage) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        return LanguageViewHolder(
            ItemLanguageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val currLanguage = getItem(position)
        holder.bind(currLanguage)
    }

    inner class LanguageViewHolder(private val binding: ItemLanguageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currLanguage: AppLanguage) {
            binding.tvLanguage.text = currLanguage.language
            binding.root.setOnClickListener {
                onItemClicked?.invoke(currLanguage)
            }
        }
    }

    fun setOnLanguageItemClicked(onItemClicked: (AppLanguage) -> Unit) {
        this.onItemClicked = onItemClicked
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<AppLanguage>() {
            override fun areItemsTheSame(oldItem: AppLanguage, newItem: AppLanguage): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: AppLanguage, newItem: AppLanguage): Boolean {
                return oldItem == newItem
            }

        }
    }
}
