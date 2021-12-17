package com.example.androidgroup4.data.user

import com.example.androidgroup4.data.model.Model
import com.example.androidgroup4.data.model.User
import com.example.androidgroup4.data.user.model.request.ChangePasswordRequest
import com.example.androidgroup4.data.user.model.request.RegisterRequest
import com.example.androidgroup4.data.user.model.request.UserRequest
import com.example.androidgroup4.data.user.model.response.UserResponse
import com.example.androidgroup4.data.user.remote.UserDataSource
import com.example.androidgroup4.utils.Resource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userData: UserDataSource) :
    UserRepository {

    override suspend fun postLogin(email: String, password: String): Resource<User> {
        return userData.postLogin(email, password)
    }

    override suspend fun postRegister(registerRequest: RegisterRequest): Resource<Model> {
        return userData.postRegister(registerRequest)
    }

    override suspend fun getUserProfile(email: String): Resource<UserResponse?> {
        return userData.getUserProfile(email)
    }

    override suspend fun postResetPassword(token: String, password: String): Resource<Model> {
        return userData.postResetPassword(token, password)
    }

    override suspend fun postForgotPassword(email: String): Resource<Model> {
        return userData.postForgotPassword(email)
    }

    override suspend fun putUpdateProfile(userRequest: UserRequest): Resource<User> {
        return userData.putUpdateProfile(userRequest)

    }

    override suspend fun putChangePassword(changePasswordRequest: ChangePasswordRequest): Resource<User> {
        return userData.putChangePassword(changePasswordRequest)
    }


}