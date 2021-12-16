package com.example.androidgroup4.data.articlel

import com.example.androidgroup4.data.model.Article
import com.example.androidgroup4.utils.Resource

interface ArticleRepository {

    suspend fun getArticles(page: Int): Resource<List<Article>>

}