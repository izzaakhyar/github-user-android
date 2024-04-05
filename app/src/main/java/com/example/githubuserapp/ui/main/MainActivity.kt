package com.example.githubuserapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.ui.favorite.FavoriteUserActivity
import com.example.githubuserapp.R
import com.example.githubuserapp.ui.setting.SettingActivity
import com.example.githubuserapp.ui.setting.SettingPreferences
import com.example.githubuserapp.data.response.GithubUser
import com.example.githubuserapp.ui.setting.dataStore
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.ui.SettingPref
import com.example.githubuserapp.ui.setting.SettingViewModel
import com.example.githubuserapp.ui.user.UserAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel =
            ViewModelProvider(this, SettingPref(pref))[SettingViewModel::class.java]

        setThemeSetting(settingViewModel)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        val mainViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]
        mainViewModel.user.observe(this) {
            setUserData(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    mainViewModel.findUser(searchView.text.toString())
                    false
                }
        }

        setMenu()

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun setMenu() {
        binding.appBarMain.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_favorite -> {
                    val intent = Intent(this, FavoriteUserActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.menu_theme -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }

    private fun setUserData(items: List<GithubUser>) {
        val adapter = UserAdapter(items)
        binding.rvUsers.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun setThemeSetting(settingViewModel: SettingViewModel) {
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}