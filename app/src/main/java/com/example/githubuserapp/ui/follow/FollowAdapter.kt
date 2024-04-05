package com.example.githubuserapp.ui.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.response.UserFollowResponseItem
import com.example.githubuserapp.databinding.ItemUserBinding

class FollowAdapter :
    ListAdapter<UserFollowResponseItem, FollowAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserFollowResponseItem) {
            binding.tvUsername.text = user.login
            Glide.with(itemView.context).load(user.avatarUrl).into(binding.imgUserPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserFollowResponseItem>() {
            override fun areItemsTheSame(
                oldItem: UserFollowResponseItem,
                newItem: UserFollowResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: UserFollowResponseItem,
                newItem: UserFollowResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}