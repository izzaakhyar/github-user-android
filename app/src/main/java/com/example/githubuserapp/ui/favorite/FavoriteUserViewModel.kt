package com.example.githubuserapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserapp.data.FavoriteUserRepository
import kotlinx.coroutines.launch

class FavoriteUserViewModel(private val favoriteUserRepository: FavoriteUserRepository) : ViewModel() {
    fun getFavoriteUsers() = favoriteUserRepository.getFavoriteUsers()

    fun checkFavoriteUsers(username: String) =
        favoriteUserRepository.getFavoriteByUsername(username)

    fun addFavoriteUsers(name: String, avatarUrl: String) {
        viewModelScope.launch {
            favoriteUserRepository.setFavoriteUsers(
                name,
                avatarUrl
            )
        }
    }

    fun deleteFavoriteUsers(username: String) {
        viewModelScope.launch {
            favoriteUserRepository.deleteFavoriteUser(username)
        }
    }
}