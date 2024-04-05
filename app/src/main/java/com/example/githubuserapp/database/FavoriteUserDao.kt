package com.example.githubuserapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteUserEntity: FavoriteUserEntity)

    @Query("DELETE FROM favorite_users WHERE username = :username")
    suspend fun delete(username: String)

    @Query("SELECT * FROM favorite_users")
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUserEntity>>

    @Query("SELECT * FROM favorite_users WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUserEntity>
}