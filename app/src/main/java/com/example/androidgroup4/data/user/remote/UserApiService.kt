package com.example.androidgroup4.data.user.remote

import com.example.androidgroup4.data.user.model.response.UserResponse
import com.example.androidgroup4.data.user.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface UserApiService {

    companion object {
        const val BASE_URL = "https://alodokter-rakamin-fsw-grup4.herokuapp.com/api/v1/"
    }

    @POST("login")
    @FormUrlEncoded
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>


    //API MOCK
    @GET("users")
    suspend fun getAllUsers(): Response<List<UserResponse>>

    @PUT("users/{id}")
    @FormUrlEncoded
    suspend fun putEditProfile(
        @Path("id") userId: Int,
        @Field("fullname") fullName: String,
        @Field("tgl_lahir") dateOfBirth: String,
        @Field("jenis_kelamin") gender: String,
        @Field("no_ktp") idCardNumber: Long,
        @Field("alamat") address: String
    ): Response<UserResponse>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") userId: Int): Response<UserResponse>

    @GET("users")
    suspend fun postLogin(@Query("email") email: String): Response<List<UserResponse>>

    @POST("users")
    @FormUrlEncoded
    suspend fun postRegister(
        @Field("fullname") fullName: String,
        @Field("jenis_kelamin") gender: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<UserResponse>
}