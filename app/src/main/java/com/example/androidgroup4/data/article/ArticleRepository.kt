package com.example.androidgroup4.data.article

import com.example.androidgroup4.data.model.Article
import com.example.androidgroup4.utils.Resource

interface ArticleRepository {

    suspend fun getArticles(page: Int): Resource<List<Article>>

    suspend fun getSearchArticles(title: String): Resource<List<Article>>

    suspend fun getDetailArticle(id: Int): Resource<Article?>

}