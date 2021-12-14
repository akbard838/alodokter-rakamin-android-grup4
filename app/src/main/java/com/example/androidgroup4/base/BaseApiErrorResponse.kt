package com.example.androidgroup4.base

import com.example.androidgroup4.utils.emptyString

data class BaseApiErrorResponse(
    val status: String? = emptyString(),
    val message: String
)