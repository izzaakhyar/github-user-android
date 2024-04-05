package com.example.githubuserapp.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.database.FavoriteUserEntity
import com.example.githubuserapp.databinding.ItemFavoriteBinding
import com.example.githubuserapp.ui.user.UserDetailActivity

class FavoriteUserAdapter(
    private val favorite: List<FavoriteUserEntity>,
    private val onUnfavoriteClick: (String) -> Unit
) : RecyclerView.Adapter<FavoriteUserAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: FavoriteUserEntity) {
            binding.tvUsername.text = favorite.username
            Glide.with(itemView.context).load(favorite.avatarUrl).into(binding.imgUserPhoto)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataFavorite = favorite[position]
        holder.bind(dataFavorite)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UserDetailActivity::class.java)
            intent.putExtra("username", dataFavorite.username)
            holder.itemView.context.startActivity(intent)
        }

        val mbUnfavorite = holder.binding.mButtonFavorite
        mbUnfavorite.setOnClickListener {
            onUnfavoriteClick(dataFavorite.username)
        }
    }

    override fun getItemCount() = favorite.size

}