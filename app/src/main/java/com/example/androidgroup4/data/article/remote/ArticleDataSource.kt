package com.example.androidgroup4.data.article.remote

import com.example.androidgroup4.base.BaseApiErrorResponse
import com.example.androidgroup4.data.model.Article
import com.example.androidgroup4.utils.ApiErrorOperator
import com.example.androidgroup4.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleDataSource @Inject constructor(private val articleApiService: ArticleApiService) {

    suspend fun getArticles(page: Int): Resource<List<Article>> {
        return try {
            val response = articleApiService.getArticles(page)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    withContext(Dispatchers.IO) {
                        Resource.success(it.data?.map { it.toArticle() } ?: listOf())
                    }
                } ?: Resource.empty()
            } else {
                Resource.error(ApiErrorOperator.parseError(response))
            }
        } catch (e: Exception) {
            Resource.error(BaseApiErrorResponse(message = e.message.toString()))
        }
    }

    suspend fun getSearchArticles(title: String): Resource<List<Article>> {
        return try {
            val response = articleApiService.getSearchArticles(title)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    withContext(Dispatchers.IO) {
                        Resource.success(it.data?.map { it.toArticle() } ?: listOf())
                    }
                } ?: Resource.empty()
            } else {
                Resource.error(ApiErrorOperator.parseError(response))
            }
        } catch (e: Exception) {
            Resource.error(BaseApiErrorResponse(message = e.message.toString()))
        }
    }

    suspend fun getDetailArticle(id: Int): Resource<Article?> {
        return try {
            val response = articleApiService.getDetailArticle(id)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    withContext(Dispatchers.IO) {
                        Resource.success(it.data?.toArticle())
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