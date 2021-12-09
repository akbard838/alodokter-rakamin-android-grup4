package com.example.androidgroup4.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.androidgroup4.data.model.Article
import com.example.androidgroup4.MainActivity
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseFragment
import com.example.androidgroup4.databinding.FragmentHomeBinding
import com.example.androidgroup4.ui.adapter.ArticleAdapter
import com.example.androidgroup4.ui.auth.LoginActivity
import com.example.androidgroup4.ui.detail.DetailArticleActivity
import com.example.androidgroup4.ui.profile.ProfileActivity
import com.synnapps.carouselview.ImageListener

class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    var imageArray:ArrayList<Int> = ArrayList()

    private lateinit var articleAdapter: ArticleAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding
        = FragmentHomeBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity?)?.setSupportActionBar(binding.toolbar)


        articleAdapter = ArticleAdapter()
        articleAdapter.setData(getDummyArticle())
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

    private fun getDummyArticle(): ArrayList<Article> {
        val articles = arrayListOf<Article>()

        val titles = resources.getStringArray(R.array.list_of_title_article)
        val descriptions = resources.getStringArray(R.array.list_of_description_article)
        val category = resources.getStringArray(R.array.list_of_category_article)
        val images = resources.getStringArray(R.array.list_of_image_article)

        for (i in titles.indices) {
            articles.add(
                Article(titles[i], descriptions[i], category[i], images[i])
            )
        }
        return articles
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        if (MainActivity.getUserLoggedInStatus(requireContext()))
            inflater.inflate(R.menu.profile_menu, menu)
        else {
            inflater.inflate(R.menu.login_menu, menu)
            val menuItem = menu.findItem(R.id.menu_login)
            menuItem.actionView.setOnClickListener {
                onOptionsItemSelected(menuItem)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_profile -> ProfileActivity.start(requireContext())
            R.id.menu_login -> LoginActivity.start(requireContext())
        }
        return super.onOptionsItemSelected(item)

    }

}