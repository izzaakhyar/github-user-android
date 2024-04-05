package com.example.githubuserapp.ui.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.response.UserDetailResponse
import com.example.githubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userDetail = MutableLiveData<UserDetailResponse>()
    val userDetail: LiveData<UserDetailResponse> = _userDetail

    private val _bio = MutableLiveData<String>()
    val bio: LiveData<String> = _bio

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _followers = MutableLiveData<Int?>()
    val followers: LiveData<Int?> = _followers

    private val _following = MutableLiveData<Int?>()
    val following: LiveData<Int?> = _following

    private val _repos = MutableLiveData<Int?>()
    val repos: LiveData<Int?> = _repos

    private val _avatar = MutableLiveData<String?>()
    val avatar: LiveData<String?> = _avatar

    companion object {
        var detail = ""
    }

    init {
        setDetail(detail)
    }

    fun setDetail(detail: String?) {
        _isLoading.value = true
        detail?.let { ApiConfig.getApiService().getDetailUser(it) }
            ?.enqueue(object : Callback<UserDetailResponse> {
                override fun onResponse(
                    call: Call<UserDetailResponse>,
                    response: Response<UserDetailResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        responseBody?.let {
                            _userDetail.postValue(response.body()!!)
                            _bio.value = it.bio.toString()
                            _name.value = it.name.toString()
                            _followers.value = it.followers
                            _following.value = it.following
                            _repos.value = it.publicRepos
                            _avatar.value = it.avatarUrl
                        }
                    } else {
                        Log.e(UserDetailActivity.TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(UserDetailActivity.TAG, "onFailure: ${t.message}")
                }
            })
    }

}