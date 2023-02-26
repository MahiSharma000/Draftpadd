package com.example.draftpad.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.draftpad.databinding.CategoryBinding
import com.example.draftpad.network.Category
import com.example.draftpad.ui.library.ReadingListFragment

class SearchAdapter: ListAdapter<Category, SearchAdapter.CategoryViewHolder>(DiffCallback) {
    class CategoryViewHolder(private val binding: CategoryBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind (category: Category){
            binding.category = category
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.CategoryViewHolder {
        return SearchAdapter.CategoryViewHolder(
            CategoryBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }
    companion object DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.name==newItem.name
        }

    }

}