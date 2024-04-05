package com.example.githubuserapp.data

import androidx.lifecycle.LiveData
import com.example.githubuserapp.data.retrofit.ApiService
import com.example.githubuserapp.database.FavoriteUserDao
import com.example.githubuserapp.database.FavoriteUserEntity

class FavoriteUserRepository private constructor(
    private val apiService: ApiService,
    private val favoriteUserDao: FavoriteUserDao
) {

    fun getFavoriteUsers(): LiveData<List<FavoriteUserEntity>> {
        return favoriteUserDao.getAllFavoriteUsers()
    }

    fun getFavoriteByUsername(username: String): LiveData<FavoriteUserEntity> {
        return favoriteUserDao.getFavoriteUserByUsername(username)
    }

    suspend fun deleteFavoriteUser(username: String) {
        favoriteUserDao.delete(username)
    }

    suspend fun setFavoriteUsers(username: String, avatarUrl: String) {
        favoriteUserDao.insert(FavoriteUserEntity(username, avatarUrl))
    }

    companion object {
        @Volatile
        private var instance: FavoriteUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteUserDao: FavoriteUserDao
        ): FavoriteUserRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteUserRepository(apiService, favoriteUserDao)
            }.also { instance = it }
    }
}