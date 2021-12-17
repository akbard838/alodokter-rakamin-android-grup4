package com.example.androidgroup4.ui.article

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivityDetailArticleBinding
import com.example.androidgroup4.ui.viewmodel.ArticleViewModel
import com.example.androidgroup4.utils.*
import com.example.androidgroup4.utils.constant.BundleKeys
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailActivity : BaseActivity<ActivityDetailArticleBinding>() {

    companion object {
        fun start(context: Context, articleId: Int) {
            Intent(context, ArticleDetailActivity::class.java).apply {
                putExtra(BundleKeys.ARTICLE_ID, articleId)
                context.startActivity(this)
            }
        }
    }

    private val articleViewModel: ArticleViewModel by viewModels()

    private var articleId: Int? = null

    override val bindingInflater: (LayoutInflater) -> ViewBinding =
        ActivityDetailArticleBinding::inflate

    override fun initIntent() {
        articleId = intent.getIntExtra(BundleKeys.ARTICLE_ID, 0)
    }

    override fun initUI() {
        articleId?.let {
            articleViewModel.getDetailArticle(it)
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
                    binding.pbDetailArticle.visible()
                }
                is Resource.Success -> {
                    binding.pbDetailArticle.gone()

                    it.data?.let { article ->
                        setupToolbar(binding.toolbar, true, emptyString())
                        with(binding) {
                            ivDetailImage.setImageUrl(
                                this@ArticleDetailActivity,
                                article.imageUrl,
                                pbImageArticle,
                                R.drawable.img_not_available
                            )
                            tvTitle.text = article.title
                            tvDescription.text = article.description
                        }
                    }
                }
                is Resource.Error -> {
                    binding.pbDetailArticle.gone()
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