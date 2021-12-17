package com.example.androidgroup4.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidgroup4.data.article.ArticleRepository
import com.example.androidgroup4.data.model.Article
import com.example.androidgroup4.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _articles = MutableLiveData<Resource<List<Article>>>()
    val articles: LiveData<Resource<List<Article>>> by lazy { _articles }

    private val _searchArticles = MutableLiveData<Resource<List<Article>>>()
    val searchArticles: LiveData<Resource<List<Article>>> by lazy { _searchArticles }

    private val _detailArticle = MutableLiveData<Resource<Article?>>()
    val detailArticle: LiveData<Resource<Article?>> by lazy { _detailArticle }

    init {
        _articles.value = Resource.init()
        _searchArticles.value = Resource.init()
    }

    fun getArticles(page: Int) {
        viewModelScope.launch {
            _articles.value = Resource.loading()
            val data = articleRepository.getArticles(page)
            _articles.value = data
        }
    }

    fun getSearchArticles(title: String) {
        viewModelScope.launch {
            _searchArticles.value = Resource.loading()
            val data = articleRepository.getSearchArticles(title)
            _searchArticles.value = data
        }
    }

    fun getDetailArticle(id: Int) {
        viewModelScope.launch {
            _detailArticle.value = Resource.loading()
            val data = articleRepository.getDetailArticle(id)
            _detailArticle.value = data
        }
    }

}