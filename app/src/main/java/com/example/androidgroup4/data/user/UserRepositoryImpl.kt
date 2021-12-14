package com.example.androidgroup4.data.user

import com.example.androidgroup4.data.model.Model
import com.example.androidgroup4.data.user.model.request.RegisterRequest
import com.example.androidgroup4.data.user.remote.UserDataSource
import com.example.androidgroup4.utils.Resource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userData: UserDataSource) :
    UserRepository {

    override suspend fun postLogin(email: String, password: String): Resource<String> {
        return userData.postLogin(email, password)
    }

    override suspend fun postRegister(registerRequest: RegisterRequest): Resource<Model> {
        return userData.postRegister(registerRequest)
    }

}