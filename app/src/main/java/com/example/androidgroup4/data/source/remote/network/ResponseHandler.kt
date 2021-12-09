package com.example.androidgroup4.data.source.remote.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun<T> result(call:suspend () -> Response<T>): Flow<ApiResponse<T?>> = flow {
    emit(ApiResponse.Loading)

    try {
        val c = call()
        c.let {
            if (c.isSuccessful){
                emit(ApiResponse.Success(it.body()))
            } else {
                c.errorBody()?.let { e ->
                    e.close()
                    emit(ApiResponse.Failure(e.toString()))
                }
            }
        }
    } catch (t: Throwable){
        t.printStackTrace()
        emit(ApiResponse.Failure(t.message.toString()))
    }
}