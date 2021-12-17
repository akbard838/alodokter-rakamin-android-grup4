package com.example.androidgroup4.ui.article

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.data.model.Article
import com.example.androidgroup4.databinding.ActivityDetailArticleBinding
import com.example.androidgroup4.ui.viewmodel.ArticleViewModel
import com.example.androidgroup4.utils.*
import com.example.androidgroup4.utils.constant.BundleKeys
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailActivity : BaseActivity<ActivityDetailArticleBinding>() {

    companion object {
        fun start(context: Context, article: Article) {
            Intent(context, ArticleDetailActivity::class.java).apply {
                putExtra(BundleKeys.ARTICLE, article)
                context.startActivity(this)
            }
        }
    }

    private val articleViewModel: ArticleViewModel by viewModels()

    private var article: Article? = null

    override val bindingInflater: (LayoutInflater) -> ViewBinding =
        ActivityDetailArticleBinding::inflate

    override fun initIntent() {
        article = intent.getParcelableExtra(BundleKeys.ARTICLE)
    }

    override fun initUI() {

        article?.let { article ->
            articleViewModel.getDetailArticle(article.id)
        }

    }

    override fun initAction() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun initProcess() {

    }

    override fun initObservable() {
        articleViewModel.detailArticle.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    hideLoading()

                    it.data?.let { article ->
                        setupToolbar(binding.toolbar, true, emptyString())
                        with(binding) {
                            ivDetailImage.setImageUrl(
                                this@ArticleDetailActivity,
                                article.imageUrl.toHttps(),
                                R.drawable.img_not_available
                            )
                            tvTitle.text = article.title
                            tvDescription.text = article.description
                        }
                    }
                }
                is Resource.Error -> {
                    hideLoading()
                    showToast(this, it.apiError.message)
                }
                else -> {}
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

}