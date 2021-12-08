package com.example.androidgroup4.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.Article
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivityDetailArticleBinding

class DetailArticleActivity : BaseActivity<ActivityDetailArticleBinding>() {

    companion object {
        const val EXTRA_ARTICLE = "extra_article"
    }

    override val bindingInflater: (LayoutInflater) -> ViewBinding = ActivityDetailArticleBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        val article = intent.getParcelableExtra<Article>(EXTRA_ARTICLE)
        article?.let {
            setupToolbar(binding.toolbar, true, it.title)
        }
    }

    override fun initAction() {

    }

    override fun initProcess() {

    }

    override fun initObservable() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

}