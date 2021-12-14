package com.example.androidgroup4.data.user.remote

import com.example.androidgroup4.base.BaseApiErrorResponse
import com.example.androidgroup4.data.model.Model
import com.example.androidgroup4.data.user.model.request.RegisterRequest
import com.example.androidgroup4.utils.ApiErrorOperator
import com.example.androidgroup4.utils.Resource
import com.example.androidgroup4.utils.emptyString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDataSource @Inject constructor(private val userApiService: UserApiService) {

    suspend fun postLogin(email: String, password: String): Resource<String> {
        return try {
            val response = userApiService.postLogin(email, password)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    withContext(Dispatchers.IO) {
                        Resource.success(it.token ?: emptyString())
                    }
                } ?: Resource.empty()
            } else {
                Resource.error(ApiErrorOperator.parseError(response))
            }
        } catch (e: Exception) {
            Resource.error(BaseApiErrorResponse(message = e.message.toString()))
        }
    }

    suspend fun postRegister(registerRequest: RegisterRequest): Resource<Model> {
        return try {
            val response = userApiService.postRegister(
                name = registerRequest.name,
                email = registerRequest.email,
                password = registerRequest.password,
                address = registerRequest.address ?: "-",
                idCardNumber = registerRequest.idCardNumber ?: "-",
                birthDate = registerRequest.birthDate ?: "-",
                gender = registerRequest.gender
            )
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    withContext(Dispatchers.IO) {
                        Resource.success(Model())
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