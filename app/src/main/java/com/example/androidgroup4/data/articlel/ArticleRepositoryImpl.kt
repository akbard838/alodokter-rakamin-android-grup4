package com.example.androidgroup4.data.articlel

import com.example.androidgroup4.data.articlel.remote.ArticleDataSource
import com.example.androidgroup4.data.model.Article
import com.example.androidgroup4.utils.Resource
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(private val articleData: ArticleDataSource) :
    ArticleRepository {

    override suspend fun getArticles(page: Int): Resource<List<Article>> {
        return articleData.getArticles(page)
    }

}