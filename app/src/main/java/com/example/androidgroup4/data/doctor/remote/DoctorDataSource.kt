package com.example.androidgroup4.data.doctor.remote


import com.example.androidgroup4.base.BaseApiErrorResponse
import com.example.androidgroup4.data.model.Article
import com.example.androidgroup4.data.model.Doctor
import com.example.androidgroup4.utils.ApiErrorOperator
import com.example.androidgroup4.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DoctorDataSource @Inject constructor(private val doctorApiService: DoctorApiService) {

    suspend fun getDoctors(page: Int): Resource<List<Doctor>> {
        return try {
            val response = doctorApiService.getDoctors(page)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    withContext(Dispatchers.IO) {
                        Resource.success(it.data?.map { it.toDoctor() } ?: listOf())
                    }
                } ?: Resource.empty()
            } else {
                Resource.error(ApiErrorOperator.parseError(response))
            }
        } catch (e: Exception) {
            Resource.error(BaseApiErrorResponse(message = e.message.toString()))
        }
    }

    suspend fun getSearchDoctors(fullName: String): Resource<List<Doctor>> {
        return try {
            val response = doctorApiService.getSearchDoctors(fullName)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    withContext(Dispatchers.IO) {
                        Resource.success(it.data?.map { it.toDoctor() } ?: listOf())
                    }
                } ?: Resource.empty()
            } else {
                Resource.error(ApiErrorOperator.parseError(response))
            }
        } catch (e: Exception) {
            Resource.error(BaseApiErrorResponse(message = e.message.toString()))
        }
    }

    suspend fun getDetailDoctor(id: Int): Resource<Doctor?> {
        return try {
            val response = doctorApiService.getDetailDoctor(id)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    withContext(Dispatchers.IO) {
                        Resource.success(it.data?.toDoctor())
                    }
                } ?: Resource.empty()
            }else {
                Resource.error(ApiErrorOperator.parseError(response))
            }
        } catch (e: Exception) {
            Resource.error(BaseApiErrorResponse(message = e.message.toString()))
        }
    }

}