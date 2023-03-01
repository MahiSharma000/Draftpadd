package com.example.draftpad.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.draftpad.databinding.CategoryBinding
import com.example.draftpad.network.Category

class CategoryAdapter(
    val listener: (Category) -> Unit
) : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(DiffCallback) {
    class CategoryViewHolder(private val binding: CategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: com.example.draftpad.network.Category) {
            binding.category = category
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder {
        return CategoryAdapter.CategoryViewHolder(
            CategoryBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<com.example.draftpad.network.Category>() {
        override fun areItemsTheSame(
            oldItem: com.example.draftpad.network.Category,
            newItem: com.example.draftpad.network.Category
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: com.example.draftpad.network.Category,
            newItem: com.example.draftpad.network.Category
        ): Boolean {
            return oldItem.name == newItem.name
        }

    }
}