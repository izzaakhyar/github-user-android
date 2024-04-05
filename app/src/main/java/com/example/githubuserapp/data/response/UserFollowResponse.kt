package com.example.githubuserapp.data.response

import com.google.gson.annotations.SerializedName

data class UserFollowResponse(

	@field:SerializedName("UserFollowResponse")
	val userFollowResponse: List<UserFollowResponseItem?>? = null
)

data class UserFollowResponseItem(

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,
)
