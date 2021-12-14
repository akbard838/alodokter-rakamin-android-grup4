package com.example.androidgroup4.data.user

import com.example.androidgroup4.data.user.model.response.LoginResponse
import com.example.androidgroup4.utils.Resource

interface UserRepository {

    suspend fun postLogin(email: String, password: String): Resource<LoginResponse>

}