package com.example.androidgroup4.ui

import androidx.lifecycle.ViewModel
import com.example.androidgroup4.data.source.remote.UserRepository
import com.example.androidgroup4.data.source.remote.network.ApiResponse
import com.example.androidgroup4.data.source.remote.network.result
import com.example.androidgroup4.data.source.remote.response.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun getUsers(): Flow<ApiResponse<List<UserResponse>?>> {
        return userRepository.getUsers()
    }

    fun getUserById(userId: Int): Flow<ApiResponse<UserResponse?>> {
        return userRepository.getUserById(userId)
    }

    fun putEditProfile(
        userId: Int,
        fullName: String,
        dateOfBirth: String,
        gender: String,
        idCardNumber: Long,
        address: String
    ): Flow<ApiResponse<UserResponse?>> {
        return userRepository.putEditProfile(
            userId,
            fullName,
            dateOfBirth,
            gender,
            idCardNumber,
            address
        )
    }

    fun postLogin(email: String, password: String): Flow<ApiResponse<List<UserResponse>?>> {
        return userRepository.postLogin(email)
    }

    fun postRegister(email: String, password: String): Flow<ApiResponse<UserResponse?>> {
        return userRepository.postRegister(email, password)
    }

}
