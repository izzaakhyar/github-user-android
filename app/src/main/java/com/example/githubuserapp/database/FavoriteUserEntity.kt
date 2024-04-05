package com.example.githubuserapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_users")
data class FavoriteUserEntity(
    @ColumnInfo(name = "username")
    @PrimaryKey(autoGenerate = false)
    var username: String = "",

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null,
)