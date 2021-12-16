package com.example.androidgroup4.data.articlel.remote

import com.example.androidgroup4.base.BaseApiResponse
import com.example.androidgroup4.data.articlel.model.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApiService {

    @GET("articles")
    suspend fun getArticles(
        @Query("page") page: Int
    ) : Response<BaseApiResponse<List<ArticleResponse>>>

}