package com.example.androidgroup4.data.source.remote

import com.example.androidgroup4.data.source.remote.network.UserApiService
import com.example.androidgroup4.data.source.remote.network.result
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApiService: UserApiService) {
    fun getUsers() = result {
        userApiService.getAllUsers()
    }

    fun getUserById(userId: Int) = result {
        userApiService.getUserById(userId)
    }

    fun postLogin(email: String) = result {
        userApiService.postLogin(email)
    }

    fun postRegister(email: String, password: String) = result {
        userApiService.postRegister(email, password)
    }
}