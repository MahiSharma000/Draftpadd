package com.example.draftpad.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.draftpad.databinding.BookListItemBinding
import com.example.draftpad.databinding.ProfileListItemBinding
import com.example.draftpad.network.Book
import com.example.draftpad.network.Follower
import com.example.draftpad.ui.search.BookAdapter

class FollowerAdapter(
    val listener: (Follower) -> Unit
) : ListAdapter<Follower, FollowerAdapter.FollowerViewHolder>(DiffCallback) {
    class FollowerViewHolder(private val binding: ProfileListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(follower: Follower) {
            binding.follower = follower
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowerAdapter.FollowerViewHolder {
        return FollowerAdapter.FollowerViewHolder(
           ProfileListItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: FollowerAdapter.FollowerViewHolder, position: Int) {
        val follower = getItem(position)
        holder.bind(follower)
        holder.itemView.setOnClickListener {
            listener(follower)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<com.example.draftpad.network.Follower>() {
        override fun areItemsTheSame(
            oldItem: com.example.draftpad.network.Follower,
            newItem: com.example.draftpad.network.Follower
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: com.example.draftpad.network.Follower,
            newItem: com.example.draftpad.network.Follower
        ): Boolean {
            return oldItem.user_id == newItem.user_id
        }

    }
}