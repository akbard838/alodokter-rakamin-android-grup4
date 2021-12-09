package com.example.androidgroup4.ui.article

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.data.model.Article
import com.example.androidgroup4.databinding.ActivityDetailArticleBinding
import com.example.androidgroup4.ui.doctor.DoctorDetailActivity
import com.example.androidgroup4.utils.constant.BundleKeys
import com.example.androidgroup4.utils.setImageUrl

class DetailArticleActivity : BaseActivity<ActivityDetailArticleBinding>() {

    companion object {
        fun start(context: Context, article: Article) {
            Intent(context, DoctorDetailActivity::class.java).apply {
                putExtra(BundleKeys.ARTICLE, article)
                context.startActivity(this)
            }
        }
    }

    private var article: Article? = null

    override val bindingInflater: (LayoutInflater) -> ViewBinding = ActivityDetailArticleBinding::inflate

    override fun initIntent() {
        article = intent.getParcelableExtra(BundleKeys.ARTICLE)
    }

    override fun initUI() {
        article?.let { article ->
            setupToolbar(binding.toolbar, true, article.title)

            with(binding) {
                ivDetailImage.setImageUrl(this@DetailArticleActivity, article.image, R.drawable.img_not_available)
                tvDescription.text = article.description
            }
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
            android.R.id.home -> onBackPressed()
            R.id.menu_share -> Toast.makeText(this, "Share Article", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.share_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}