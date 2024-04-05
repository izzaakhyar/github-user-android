package com.example.githubuserapp.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapp.ui.favorite.FavoriteUserActivity
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.ActivityUserDetailBinding
import com.example.githubuserapp.ui.ViewModelFactory
import com.example.githubuserapp.ui.favorite.FavoriteUserViewModel
import com.example.githubuserapp.ui.follow.SectionsPagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private var isFavorite = false

    companion object {
        const val TAG = "UserDetailActivity"

        private var detail: String? = null

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers_text,
            R.string.following_text
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detail = intent.getStringExtra("username")

        val appBar = binding.appBarMain.topAppBar
        appBar.isTitleCentered = true

        setSupportActionBar(binding.appBarMain.topAppBar)
        supportActionBar?.title = "$detail"

        binding.tvFollower.text = getString(R.string.followers_text)
        binding.tvFollowing.text = getString(R.string.following_text)
        binding.tvRepo.text = getString(R.string.repository)

        val detailViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[UserDetailViewModel::class.java]

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val viewModel: FavoriteUserViewModel by viewModels {
            factory
        }

        checkFavorite(viewModel)

        detailViewModel.userDetail.observe(this) { userDetail ->
            binding.fabStar.setOnClickListener {
                if (isFavorite) {
                    userDetail.login?.let { username -> viewModel.deleteFavoriteUsers(username) }
                    showSnackbar("$detail removed from favorite")
                } else {
                    viewModel.addFavoriteUsers(userDetail.login ?: "", userDetail.avatarUrl ?: "")
                    showSnackbar("$detail added to favorite")
                }
            }
        }

        detailViewModel.setDetail(detail)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        if (detail != null) {
            sectionsPagerAdapter.username = detail as String
        }
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.bio.observe(this) {
            when {
                it != "null" -> binding.tvBio.text = it
                else -> binding.tvBio.visibility = View.GONE
            }
        }

        detailViewModel.name.observe(this) {
            when {
                it != "null" -> binding.tvUsername.text = it
                else -> binding.tvUsername.visibility = View.GONE
            }
        }

        detailViewModel.avatar.observe(this) {
            Glide.with(this)
                .load(it)
                .into(binding.imgPhotoProfile)
        }

        detailViewModel.repos.observe(this) {
            binding.tvTotalRepo.text = it.toString()
        }

        detailViewModel.followers.observe(this) {
            binding.tvTotalFollower.text = it.toString()
        }

        detailViewModel.following.observe(this) {
            binding.tvTotalFollowing.text = it.toString()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.viewPager, message, Snackbar.LENGTH_SHORT)
            .setAction("SEE FAVORITE") {
                val intent = Intent(this, FavoriteUserActivity::class.java)
                startActivity(intent)
            }.show()
    }

    private fun checkFavorite(viewModel: FavoriteUserViewModel) {
        detail?.let {
            viewModel.checkFavoriteUsers(it).observe(this) { data ->
                if (data == null) {
                    binding.fabStar.setIconResource(R.drawable.baseline_star_outline_24)
                    binding.fabStar.setText(R.string.favorite)
                    this.isFavorite = false
                } else {
                    binding.fabStar.setIconResource(R.drawable.baseline_star_24)
                    binding.fabStar.setText(R.string.unfavorite)
                    this.isFavorite = true
                }
            }
        }
    }
}