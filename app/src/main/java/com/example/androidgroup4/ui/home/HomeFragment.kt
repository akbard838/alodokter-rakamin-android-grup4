package com.example.androidgroup4.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.androidgroup4.Article
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseFragment
import com.example.androidgroup4.databinding.FragmentHomeBinding
import com.example.androidgroup4.ui.detail.DetailArticleFragment
import com.synnapps.carouselview.ImageListener

class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    var imageArray:ArrayList<Int> = ArrayList()

    private lateinit var articleAdapter: ArticleAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding
        = FragmentHomeBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        (activity as AppCompatActivity?)?.setSupportActionBar(binding.toolbar)

        articleAdapter = ArticleAdapter()
        articleAdapter.setData(dummyData())
        with(binding.rvArticle) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = articleAdapter
        }

        //carousel image
        imageArray.add(R.drawable.img1)
        imageArray.add(R.drawable.img2)
        imageArray.add(R.drawable.img3)

        with(binding) {
            carouselView.setImageListener(imageListener)
            carouselView.pageCount = imageArray.size
        }
    }

    var imageListener = ImageListener { position, imageView ->
        Glide.with(requireContext())
            .load(imageArray[position])
            .into(imageView)

        imageView.setOnClickListener {
            Toast.makeText(requireContext(), "Open Image", Toast.LENGTH_SHORT).show()   //tes
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
            Article("kesehatan", "menjaga kesehatan", "hidup sehat"),
            Article("olahraga", "berolahraga", "olahraga"),
            Article("makanan", "makan siang", "food"),
            Article("kesehatan", "menjaga kesehatan", "hidup sehat"),
            Article("olahraga", "berolahraga", "olahraga"),
            Article("makanan", "makan siang","food"),
            Article("kesehatan", "menjaga kesehatan","hidup sehat"),
            Article("olahraga", "berolahraga", "olahraga"),
            Article("makanan", "makan siang", "food"),
            Article("kesehatan", "menjaga kesehatan", "hidup sehat"),
            Article("olahraga", "berolahraga", "olahraga"),
            Article("makanan", "makan siang", "food"),
            Article("kesehatan", "menjaga kesehatan", "hidup sehat"),
            Article("olahraga", "berolahraga", "olahraga"),
            Article("makanan", "makan siang","food"),
            Article("kesehatan", "menjaga kesehatan","hidup sehat"),
            Article("olahraga", "berolahraga", "olahraga"),
            Article("makanan", "makan siang", "food")
        )
    }
}