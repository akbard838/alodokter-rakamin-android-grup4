package com.example.androidgroup4.data.source.remote.network

import com.example.androidgroup4.data.source.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface UserApiService {
    companion object {
        const val BASE_URL = "https://61aee28d2cdd90001740764c.mockapi.io/api/v1/"
    }

    @GET("users")
    suspend fun getAllUsers(): Response<List<UserResponse>>

    @GET("users")
    suspend fun postLogin(@Query("email") email: String): Response<List<UserResponse>>

    @POST("users")
    @FormUrlEncoded
    suspend fun postRegister(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<UserResponse>
}