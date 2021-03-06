package com.example.androidgroup4.data.user

import com.example.androidgroup4.data.model.Model
import com.example.androidgroup4.data.model.User
import com.example.androidgroup4.data.user.model.request.ChangePasswordRequest
import com.example.androidgroup4.data.user.model.request.RegisterRequest
import com.example.androidgroup4.data.user.model.request.UserRequest
import com.example.androidgroup4.data.user.model.response.UserResponse
import com.example.androidgroup4.utils.Resource

interface UserRepository {

    suspend fun postLogin(email: String, password: String): Resource<User>

    suspend fun postRegister(registerRequest: RegisterRequest): Resource<Model>

    suspend fun getUserProfile(email: String): Resource<UserResponse?>

    suspend fun postResetPassword(token: String, password: String): Resource<Model>

    suspend fun postForgotPassword(email: String): Resource<Model>

    suspend fun putUpdateProfile(userRequest: UserRequest): Resource<User>

    suspend fun putChangePassword(changePasswordRequest: ChangePasswordRequest): Resource<User>

}