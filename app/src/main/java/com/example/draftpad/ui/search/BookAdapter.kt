package com.example.draftpad.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.draftpad.databinding.BookListItemBinding
import com.example.draftpad.network.Book

class BookAdapter(
    val listener: (Book) -> Unit
) : ListAdapter<Book, BookAdapter.BookViewHolder>(DiffCallback) {
    class BookViewHolder(private val binding: BookListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.book = book
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookAdapter.BookViewHolder {
        return BookAdapter.BookViewHolder(
            BookListItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: BookAdapter.BookViewHolder, position: Int) {
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

        object Layout {
            val HORIZONTAL = 1
        }

    }
}