package com.example.githubuserapp.ui.favorite

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.ActivityFavoriteUserBinding
import com.example.githubuserapp.ui.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appBar = binding.appBarMain.topAppBar
        appBar.isTitleCentered = true

        setSupportActionBar(binding.appBarMain.topAppBar)
        supportActionBar?.title = getString(R.string.your_favorite)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val viewModel: FavoriteUserViewModel by viewModels {
            factory
        }

        viewModel.getFavoriteUsers().observe(this) {
            val adapter = FavoriteUserAdapter(it) { username ->
                viewModel.deleteFavoriteUsers(username)
                showSnackbar("$username removed from favorite")
            }
            binding.rvFavorite.adapter = adapter
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(
            binding.rvFavorite, message, Snackbar.LENGTH_SHORT
        ).show()
    }
}