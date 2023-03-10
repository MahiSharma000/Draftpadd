package com.example.draftpad.ui.read

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
//import com.example.draftpad.databinding.CommentListItemBinding
import com.example.draftpad.network.Comment


/*class CommentAdapter (
    val listener: (Comment) -> Unit
) : ListAdapter<Comment, CommentAdapter.CommentViewHolder>(DiffCallback) {
    class CommentViewHolder(private val binding: CommentListItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment:Comment) {
            binding.comment = comment
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentAdapter.CommentViewHolder {
        return CommentAdapter.CommentViewHolder(
            CommentListItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CommentAdapter.CommentViewHolder, position: Int) {
        val comment = getItem(position)
        holder.bind(comment)
        holder.itemView.setOnClickListener {
            listener(comment)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<com.example.draftpad.network.Comment>() {
        override fun areItemsTheSame(
            oldItem: com.example.draftpad.network.Comment,
            newItem: com.example.draftpad.network.Comment
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: com.example.draftpad.network.Comment,
            newItem: com.example.draftpad.network.Comment
        ): Boolean {
            return oldItem.content == newItem.content
        }

    }
}*/