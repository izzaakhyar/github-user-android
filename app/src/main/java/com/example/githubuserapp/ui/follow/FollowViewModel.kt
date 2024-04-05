package com.example.githubuserapp.ui.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.response.UserFollowResponseItem
import com.example.githubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private val _user = MutableLiveData<List<UserFollowResponseItem>???>()
    val user: LiveData<List<UserFollowResponseItem>?> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private var username = ""
    }

    init {
        showFollowers(username)
        showFollowing(username)
    }

    fun showFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUserFollowing(username)
        client.enqueue(object : Callback<List<UserFollowResponseItem>> {
            override fun onResponse(
                call: Call<List<UserFollowResponseItem>>,
                response: Response<List<UserFollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _user.value = responseBody
                    }
                } else {
                    Log.e(FollowFragment.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserFollowResponseItem>>, t: Throwable) {
                _isLoading.value = true
                Log.e(FollowFragment.TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun showFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUserFollowers(username)
        client.enqueue(object : Callback<List<UserFollowResponseItem>> {
            override fun onResponse(
                call: Call<List<UserFollowResponseItem>>,
                response: Response<List<UserFollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _user.value = responseBody
                    }
                } else {
                    Log.e(FollowFragment.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserFollowResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(FollowFragment.TAG, "onFailure: ${t.message}")
            }
        })
    }
}