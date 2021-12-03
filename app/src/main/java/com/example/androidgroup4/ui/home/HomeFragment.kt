package com.example.androidgroup4.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.Article
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseFragment
import com.example.androidgroup4.databinding.FragmentHomeBinding
import com.example.androidgroup4.ui.detail.DetailArticleFragment

class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    private lateinit var articleAdapter: ArticleAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding
        = FragmentHomeBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        articleAdapter = ArticleAdapter()
        articleAdapter.setData(dummyData())
        with(binding.rvArticle) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = articleAdapter
        }
    }

    override fun initAction() {
        articleAdapter.onItemClick = { article ->
            // send data
            val mBundle = Bundle()
            mBundle.putParcelable(DetailArticleFragment.EXTRA_ARTICLE, article)
            // untuk pindah fragmment
            findNavController().navigate(R.id.action_navigation_home_to_detailArticleFragment, mBundle)
        }
    }

    override fun initProcess() {

    }

    override fun initObservable() {

    }

    //Create Dummy Data
    private fun dummyData(): List<Article> {
        return listOf(
            Article("kesehatan", "menjaga kesehatan"),
            Article("olahraga", "berolahraga"),
            Article("makanan", "makan siang"),
            Article("kesehatan", "menjaga kesehatan"),
            Article("olahraga", "berolahraga"),
            Article("makanan", "makan siang"),
            Article("kesehatan", "menjaga kesehatan"),
            Article("olahraga", "berolahraga"),
            Article("makanan", "makan siang")
        )
    }
}