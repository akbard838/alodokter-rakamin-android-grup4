package com.example.androidgroup4.data.user.remote

import com.example.androidgroup4.base.BaseApiErrorResponse
import com.example.androidgroup4.data.user.model.response.LoginResponse
import com.example.androidgroup4.utils.ApiErrorOperator
import com.example.androidgroup4.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDataSource @Inject constructor(private val userApiService: UserApiService) {

    suspend fun postLogin(email: String, password: String): Resource<LoginResponse> {
        return try {
            val response = userApiService.postLogin(email, password)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    withContext(Dispatchers.IO) {
                        Resource.success(it)
                    }
                } ?: Resource.empty()
            } else {
                Resource.error(ApiErrorOperator.parseError(response))
            }
        } catch (e: Exception) {
            Resource.error(BaseApiErrorResponse(message = e.message.toString()))
        }
    }
}