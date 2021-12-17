package com.example.androidgroup4.data.article.remote

import com.example.androidgroup4.base.BaseApiResponse
import com.example.androidgroup4.data.article.model.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleApiService {

    @GET("articles")
    suspend fun getArticles(
        @Query("page") page: Int
    ) : Response<BaseApiResponse<List<ArticleResponse>>>

    @GET("articles/search")
    suspend fun getSearchArticles(
        @Query("title") title: String
    ) : Response<BaseApiResponse<List<ArticleResponse>>>

    @GET("articles/{id}")
    suspend fun getDetailArticle(
        @Path("id") id: Int
    ) : Response<BaseApiResponse<ArticleResponse>>

}