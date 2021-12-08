package com.example.androidgroup4.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
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
import com.example.androidgroup4.ui.detail.DetailArticleActivity
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
        (activity as AppCompatActivity?)?.supportActionBar?.title = ""


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
        imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        Glide.with(requireContext())
            .load(imageArray[position])
            .into(imageView)

        imageView.setOnClickListener {
            Toast.makeText(requireContext(), "Open Image", Toast.LENGTH_SHORT).show()   //tes
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initAction() {
        articleAdapter.onItemClick = { article ->
            // send data
            val mBundle = Bundle()
            mBundle.putParcelable(DetailArticleActivity.EXTRA_ARTICLE, article)
            // untuk pindah fragmment
            findNavController().navigate(R.id.action_navigation_home_to_detailArticleActivity, mBundle)
        }

        with(binding) {
            svArticle.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    v.performClick()
                    view?.clearFocus()
                    v.hideKeyboard()
                    if (event.rawX >= svArticle.right - svArticle.compoundDrawables[2].bounds.width()) {
                        Toast.makeText(requireContext(), "search diklik", Toast.LENGTH_SHORT).show()
                        // search article code

                        true
                    } else false
                } else false
            }
        }


    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun initProcess() {

    }

    override fun initObservable() {

    }

    //Create Dummy Data
    private fun dummyData(): List<Article> {
        val image = resources.getIdentifier("@drawable/img_article", null,activity?.packageName)
        return listOf(
            Article("kesehatan", "menjaga kesehatan", "hidup sehat", image),
            Article("olahraga", "berolahraga", "olahraga", image),
            Article("makanan", "makan siang", "food", image),
            Article("kesehatan", "menjaga kesehatan", "hidup sehat", image),
            Article("olahraga", "berolahraga", "olahraga", image),
            Article("makanan", "makan siang","food", image),
            Article("kesehatan", "menjaga kesehatan","hidup sehat", image),
            Article("olahraga", "berolahraga", "olahraga", image)
        )
    }
}