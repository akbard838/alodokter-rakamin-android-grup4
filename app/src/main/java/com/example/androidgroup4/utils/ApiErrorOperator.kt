package com.example.androidgroup4.utils

import android.util.Log
import com.example.androidgroup4.base.BaseApiErrorResponse
import com.google.gson.GsonBuilder
import retrofit2.Response
import java.io.IOException

object ApiErrorOperator {

    fun parseError(response: Response<*>?): BaseApiErrorResponse {
        val gson = GsonBuilder().create()
        val errorBase: BaseApiErrorResponse

        try {
            errorBase = gson.fromJson(response?.errorBody()?.string(), BaseApiErrorResponse::class.java)
        } catch (e: IOException) {
            e.message?.let { Log.d("error", it) }
            return BaseApiErrorResponse(message = e.message.toString())
        }
        return errorBase
    }

}