package com.example.androidgroup4.data.doctor.remote

import com.example.androidgroup4.base.BaseApiResponse
import com.example.androidgroup4.data.doctor.model.DoctorResponse
import retrofit2.Response
import retrofit2.http.GET

interface DoctorApiService {

    @GET("doctors")
    suspend fun getDoctors() : Response<BaseApiResponse<List<DoctorResponse>>>

}