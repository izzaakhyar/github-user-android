package com.example.githubuserapp.di

import android.content.Context
import com.example.githubuserapp.data.FavoriteUserRepository
import com.example.githubuserapp.data.retrofit.ApiConfig
import com.example.githubuserapp.database.FavoriteUserRoomDatabase

object Injection {
    fun provideRepository(context: Context): FavoriteUserRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteUserRoomDatabase.getDatabase(context)
        val dao = database.favoriteUserDao()
        return FavoriteUserRepository.getInstance(apiService, dao)
    }
}