package com.example.androidgroup4.data.source.remote

import com.example.androidgroup4.data.source.remote.network.UserApiService
import com.example.androidgroup4.data.source.remote.network.result
import com.example.androidgroup4.data.source.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApiService: UserApiService) {
    fun getUsers() = result {
        userApiService.getAllUsers()
    }

    fun getUserById(userId: Int) = result {
        userApiService.getUserById(userId)
    }

    fun putEditProfile(
        userId: Int,
        fullName: String,
        dateOfBirth: String,
        gender: String,
        idCardNumber: Long,
        address: String
    ) = result {
        userApiService.putEditProfile(userId, fullName, dateOfBirth, gender, idCardNumber, address)
    }

    fun postLogin(email: String) = result {
        userApiService.postLogin(email)
    }

    fun postRegister(
        fullName: String,
        gender: String,
        email: String,
        password: String
    ) = result {
        userApiService.postRegister(fullName, gender, email, password)
    }
}