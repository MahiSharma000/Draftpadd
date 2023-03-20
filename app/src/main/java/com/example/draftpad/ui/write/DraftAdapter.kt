package com.example.draftpad.ui.write

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.draftpad.databinding.BookListItemBinding
import com.example.draftpad.databinding.DraftCardBinding
import com.example.draftpad.network.Book
import com.example.draftpad.ui.search.BookAdapter

class DraftAdapter (
    val listener: (Book) -> Unit
) : ListAdapter<Book, DraftAdapter.DraftViewHolder>(DiffCallback) {
    class DraftViewHolder(private val binding: DraftCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.draftbook = book
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DraftAdapter.DraftViewHolder {
        return DraftAdapter.DraftViewHolder(
            DraftCardBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: DraftAdapter.DraftViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book)
        holder.itemView.setOnClickListener {
            listener(book)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<com.example.draftpad.network.Book>() {
        override fun areItemsTheSame(
            oldItem: com.example.draftpad.network.Book,
            newItem: com.example.draftpad.network.Book
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: com.example.draftpad.network.Book,
            newItem: com.example.draftpad.network.Book
        ): Boolean {
            return oldItem.title == newItem.title
        }

    }
}