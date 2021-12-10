package com.example.androidgroup4.ui.article

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseFragment
import com.example.androidgroup4.data.model.Article
import com.example.androidgroup4.databinding.FragmentArticleListBinding
import com.example.androidgroup4.ui.adapter.ArticleAdapter
import com.example.androidgroup4.ui.auth.LoginActivity
import com.example.androidgroup4.ui.main.MainActivity
import com.example.androidgroup4.utils.emptyString
import com.example.androidgroup4.utils.gone
import com.example.androidgroup4.utils.hideSoftKeyboard
import com.example.androidgroup4.utils.visible
import com.synnapps.carouselview.ImageListener
import java.util.regex.Pattern

class ArticleListFragment : BaseFragment<FragmentArticleListBinding>() {

    private var imageArray: ArrayList<Int> = ArrayList()

    private var imageListener = ImageListener { position, imageView ->
        imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        Glide.with(requireContext())
            .load(imageArray[position])
            .into(imageView)

        imageView.setOnClickListener {
            Toast.makeText(requireContext(), "Open Image", Toast.LENGTH_SHORT).show()   //tes
        }
    }

    private lateinit var articleAdapter: ArticleAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding =
        FragmentArticleListBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity?)?.setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.title = emptyString()


        articleAdapter = ArticleAdapter()
        articleAdapter.setData(getDummyArticle())
        with(binding.rvArticle) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = articleAdapter
        }

        addCarouselImage()
        with(binding) {
            carouselView.setImageListener(imageListener)
            carouselView.pageCount = imageArray.size
        }
    }

    private fun addCarouselImage() {
        imageArray.clear()
        imageArray.add(R.drawable.img1)
        imageArray.add(R.drawable.img2)
        imageArray.add(R.drawable.img3)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initAction() {
        articleAdapter.onItemClick = { article ->
            ArticleDetailActivity.start(requireContext(), article)
        }

        with(binding) {

            edtSearchArticle.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    articleAdapter.setData(getFilteredData())
                    checkIsDataEmpty(getFilteredData())
                    hideSoftKeyboard(requireContext(), binding.edtSearchArticle)
                    return@OnEditorActionListener true
                }
                false
            })

            edtSearchArticle.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    v.performClick()
                    view?.clearFocus()
                    hideSoftKeyboard(requireContext(), edtSearchArticle)
                    if (event.rawX >= edtSearchArticle.right - edtSearchArticle.compoundDrawables[2].bounds.width()) {
                        articleAdapter.setData(getFilteredData())
                        checkIsDataEmpty(getFilteredData())
                        true
                    } else false
                } else false
            }

            edtSearchArticle.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (edtSearchArticle.text.toString().isEmpty())
                        reloadData()
                }

                override fun afterTextChanged(s: Editable?) = Unit
            })

        }


    }

    override fun initProcess() {
        checkIsDataEmpty(getDummyArticle())

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

    private fun getFilteredData(): List<Article> {
        val filtered = getDummyArticle().filter {
            Pattern.compile(
                Pattern.quote(binding.edtSearchArticle.text.toString()),
                Pattern.CASE_INSENSITIVE
            ).matcher(it.title).find()
        }

        return filtered
    }

    private fun checkIsDataEmpty(articles: List<Article>) {
        binding.apply {
            if (articles.isEmpty()) {
                rvArticle.gone()
                layoutEmpty.layoutEmpty.visible()
                carouselView.gone()
            } else {
                rvArticle.visible()
                layoutEmpty.layoutEmpty.gone()
                carouselView.visible()
            }
        }
    }

    private fun reloadData() {
        articleAdapter.setData(getDummyArticle())
        checkIsDataEmpty(getDummyArticle())
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
            R.id.menu_profile -> findNavController().navigate(R.id.action_navigation_home_to_navigation_profile)
            R.id.menu_login -> LoginActivity.start(requireContext())
        }
        return super.onOptionsItemSelected(item)

    }

}