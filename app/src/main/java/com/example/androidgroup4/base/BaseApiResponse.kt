package com.example.androidgroup4.base

data class BaseApiResponse<T>(
    val status : String?,
    val message : String?,
    val data : T?,
    val token : String?
)