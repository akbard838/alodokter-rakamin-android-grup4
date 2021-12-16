package com.example.androidgroup4.data.user.remote

import com.example.androidgroup4.base.BaseApiResponse
import com.example.androidgroup4.data.model.Model
import com.example.androidgroup4.data.user.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface UserApiService {

    @POST("login")
    @FormUrlEncoded
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<BaseApiResponse<Model>>

    @POST("users")
    @FormUrlEncoded
    suspend fun postRegister(
        @Field("fullname") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("address") address: String,
        @Field("IdCardNumber") idCardNumber: String,
        @Field("BirthDate") birthDate: String,
        @Field("gender") gender: String
    ): Response<BaseApiResponse<UserResponse>>

    @GET("show")
    suspend fun getUserProfile(
        @Query("email") email: String
    ): Response<BaseApiResponse<UserResponse>>

    @POST("passwords/forgot")
    @FormUrlEncoded
    suspend fun postForgotPassword(@Field("email") email: String): Response<BaseApiResponse<Model>>

    @PATCH("passwords/reset")
    @FormUrlEncoded
    suspend fun postResetPassword(
        @Query("token") token: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirmation: String
    ): Response<BaseApiResponse<Model>>

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

    @PUT("users/{id}")
    @FormUrlEncoded
    suspend fun putChangePassword(
        @Path("id") userId: Int,
        @Field("password") password: String,
    ): Response<UserResponse>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") userId: Int): Response<UserResponse>

    @GET("users")
    suspend fun postLogin(@Query("email") email: String): Response<List<UserResponse>>
}