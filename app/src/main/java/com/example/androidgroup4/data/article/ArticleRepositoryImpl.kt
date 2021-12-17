package com.example.androidgroup4.data.article

import com.example.androidgroup4.data.article.remote.ArticleDataSource
import com.example.androidgroup4.data.model.Article
import com.example.androidgroup4.utils.Resource
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(private val articleData: ArticleDataSource) :
    ArticleRepository {

    override suspend fun getArticles(page: Int): Resource<List<Article>> {
        return articleData.getArticles(page)
    }

    override suspend fun getSearchArticles(title: String): Resource<List<Article>> {
        return articleData.getSearchArticles(title)
    }

    override suspend fun getDetailArticle(id: Int): Resource<Article?> {
        return articleData.getDetailArticle(id)
    }

}