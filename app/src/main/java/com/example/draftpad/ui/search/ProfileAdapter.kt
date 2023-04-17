package com.example.draftpad.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.draftpad.databinding.ProfileItemBinding
import com.example.draftpad.network.UserProfile

class ProfileAdapter(
    val listener: (UserProfile) -> Unit
) : ListAdapter<UserProfile, ProfileAdapter.ProfileViewHolder>(DiffCallback) {
    class ProfileViewHolder(private val binding: ProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: UserProfile) {
            binding.profile = profile
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileAdapter.ProfileViewHolder {
        return ProfileAdapter.ProfileViewHolder(
            ProfileItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ProfileAdapter.ProfileViewHolder, position: Int) {
        val profile = getItem(position)
        holder.bind(profile)
        holder.itemView.setOnClickListener {
            listener(profile)
        }
    }

    companion object DiffCallback :
        DiffUtil.ItemCallback<com.example.draftpad.network.UserProfile>() {
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