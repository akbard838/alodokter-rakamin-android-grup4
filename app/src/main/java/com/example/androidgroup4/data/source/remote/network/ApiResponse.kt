package com.example.androidgroup4.data.source.remote.network

sealed class ApiResponse<out T> {
    data class Success<out R>(val data: R?): ApiResponse<R>()
    data class Failure(val message: String): ApiResponse<Nothing>()
    object Loading: ApiResponse<Nothing>()
}