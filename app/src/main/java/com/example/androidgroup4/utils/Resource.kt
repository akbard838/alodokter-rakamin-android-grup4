package com.example.androidgroup4.utils

import com.example.androidgroup4.base.BaseApiErrorResponse

sealed class Resource<out T> {
    data class Loading<out T>(val loading : Boolean) : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val apiError: BaseApiErrorResponse) : Resource<Nothing>()
    data class Init<out T>(val new : Boolean) : Resource<T>()
    class Empty<out T> : Resource<T>()

    companion object {
        fun <T> loading() : Resource<T> = Loading(true)
        fun <T> success(data: T) : Resource<T> = Success(data)
        fun <T> error(baseApiErrorResponse: BaseApiErrorResponse): Resource<T> = Error(baseApiErrorResponse)
        fun <T> init() : Resource<T> = Init(true)
        fun <T> empty() : Resource<T> = Empty()
    }
}