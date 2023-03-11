package com.example.draftpad.ui.read

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.draftpad.databinding.ChapterItemBinding
import com.example.draftpad.network.Chapter


class ChapterAdapter(
    val listener: (Chapter) -> Unit
) : ListAdapter<Chapter, ChapterAdapter.ChapterViewHolder>(DiffCallback) {
    class ChapterViewHolder(private val binding: ChapterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chapter: Chapter) {
            binding.chapter = chapter
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChapterAdapter.ChapterViewHolder {
        return ChapterAdapter.ChapterViewHolder(
            ChapterItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ChapterAdapter.ChapterViewHolder, position: Int) {
        val chapter = getItem(position)
        holder.bind(chapter)
        holder.itemView.setOnClickListener {
            listener(chapter)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<com.example.draftpad.network.Chapter>() {
        override fun areItemsTheSame(
            oldItem: com.example.draftpad.network.Chapter,
            newItem: com.example.draftpad.network.Chapter
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: com.example.draftpad.network.Chapter,
            newItem: com.example.draftpad.network.Chapter
        ): Boolean {
            return oldItem.title == newItem.title
        }

    }
}