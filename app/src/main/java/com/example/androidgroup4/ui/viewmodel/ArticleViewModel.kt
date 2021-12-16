package com.example.androidgroup4.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidgroup4.data.articlel.ArticleRepository
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

    init {
        _articles.value = Resource.init()
    }

    fun getArticles(page: Int) {
        viewModelScope.launch {
            _articles.value = Resource.loading()
            val data = articleRepository.getArticles(page)
            _articles.value = data
        }
    }
}