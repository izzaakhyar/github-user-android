package com.example.githubuserapp.ui.user

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.response.GithubUser
import com.example.githubuserapp.databinding.ItemUserBinding

class UserAdapter(private val user: List<GithubUser>) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    class MyViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubUser) {
            binding.tvUsername.text = user.login
            Glide.with(itemView.context).load(user.avatarUrl).into(binding.imgUserPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return user.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataUser = user[position]
        holder.bind(dataUser)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UserDetailActivity::class.java)
            intent.putExtra("username", dataUser.login)
            holder.itemView.context.startActivity(intent)
        }
    }
}