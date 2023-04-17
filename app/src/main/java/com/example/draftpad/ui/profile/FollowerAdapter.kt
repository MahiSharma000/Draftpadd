package com.example.draftpad.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.draftpad.databinding.ProfileItemBinding
import com.example.draftpad.network.UserProfile

class FollowerAdapter(
    val listener: (UserProfile) -> Unit
) : ListAdapter<UserProfile, FollowerAdapter.FollowerViewHolder>(DiffCallback) {
    class FollowerViewHolder(private val binding: ProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(follower: UserProfile) {
            binding.profile = follower
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowerAdapter.FollowerViewHolder {
        return FollowerAdapter.FollowerViewHolder(
           ProfileItemBinding.inflate(
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

    companion object DiffCallback : DiffUtil.ItemCallback<com.example.draftpad.network.UserProfile>() {
        override fun areItemsTheSame(
            oldItem: com.example.draftpad.network.UserProfile,
            newItem: com.example.draftpad.network.UserProfile
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: com.example.draftpad.network.UserProfile,
            newItem: com.example.draftpad.network.UserProfile
        ): Boolean {
            return oldItem.user_id == newItem.user_id
        }

    }
}