package com.example.androidgroup4.data.doctor.remote

import com.example.androidgroup4.base.BaseApiResponse
import com.example.androidgroup4.data.article.model.ArticleResponse
import com.example.androidgroup4.data.doctor.model.DoctorResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DoctorApiService {

    @GET("doctors")
    suspend fun getDoctors(
        @Query("page") page: Int
    ) : Response<BaseApiResponse<List<DoctorResponse>>>

    @GET("doctors/search")
    suspend fun getSearchDoctors(
        @Query("fullname") fullName: String
    ) : Response<BaseApiResponse<List<DoctorResponse>>>

    @GET("doctors/{id}")
    suspend fun getDetailDoctor(
        @Path("id") id: Int
    ) : Response<BaseApiResponse<DoctorResponse>>

}