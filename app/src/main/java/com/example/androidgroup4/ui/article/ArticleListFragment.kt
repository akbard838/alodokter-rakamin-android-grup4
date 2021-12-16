package com.example.androidgroup4.ui.article

import android.annotation.SuppressLint
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseFragment
import com.example.androidgroup4.data.model.Article
import com.example.androidgroup4.databinding.FragmentArticleListBinding
import com.example.androidgroup4.ui.adapter.ArticleAdapter
import com.example.androidgroup4.ui.auth.LoginActivity
import com.example.androidgroup4.ui.main.MainActivity
import com.example.androidgroup4.ui.viewmodel.ArticleViewModel
import com.example.androidgroup4.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleListFragment : BaseFragment<FragmentArticleListBinding>() {

    private lateinit var articleAdapter: ArticleAdapter

    private val articleViewModel: ArticleViewModel by viewModels()

    private lateinit var layoutManager: LinearLayoutManager

    private var articles: ArrayList<Article> = arrayListOf()

    private var isLoading = false

    private var isDefault = true

    private var page = 1

    private var isLast = false

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding =
        FragmentArticleListBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity?)?.setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.title = emptyString()

        layoutManager = LinearLayoutManager(requireContext())
        articleAdapter = ArticleAdapter()
        binding.rvArticle.setHasFixedSize(true)
        binding.rvArticle.layoutManager = layoutManager
        binding.rvArticle.adapter = articleAdapter

        getArticles()

        binding.rvArticle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = articleAdapter.itemCount
                if (!isLoading && !isLast) {
                    if (visibleItemCount + pastVisibleItem >= total) {
                        page++
                        getArticles()
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initAction() {
        articleAdapter.onItemClick = { article ->
            ArticleDetailActivity.start(requireContext(), article)
        }

        with(binding) {

            edtSearchArticle.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    articleViewModel.getSearchArticles(edtSearchArticle.text.toString())
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
                        articleViewModel.getSearchArticles(edtSearchArticle.text.toString())
                        true
                    } else false
                } else false
            }

            edtSearchArticle.doAfterTextChanged {
                if (edtSearchArticle.text.toString().isEmpty()) loadDefaultData()
            }

        }

    }

    override fun initProcess() {
    }

    override fun initObservable() {
        articleViewModel.articles.observe(this, {
            when (it) {
                is Resource.Loading -> {
                    isLoading = true
                    binding.pbArticle.visible()
                }
                is Resource.Success -> {
                    articles.addAll(it.data)
                    articleAdapter.setData(articles)
                    checkIsDataEmpty(articles)

                    if (it.data.isEmpty()) {
                        binding.pbArticle.gone()
                        isLast = true
                    } else binding.pbArticle.invisible()

                    isLoading = false
                }
                is Resource.Error -> {
                    isLoading = false
                    binding.pbArticle.gone()
                    showToast(requireContext(), it.apiError.message)
                }
                else -> {}
            }
        })

        articleViewModel.searchArticles.observe(this, {
            when (it) {
                is Resource.Loading -> {
                    binding.pbArticle.visible()
                }
                is Resource.Success -> {
                    binding.pbArticle.gone()
                    articles.clear()
                    articles.addAll(it.data)
                    articleAdapter.setData(articles)
                    checkIsDataEmpty(articles)
                    isLast = true
                }
                is Resource.Error -> {
                    binding.pbArticle.gone()
                    showToast(requireContext(), it.apiError.message)
                }
                else -> {}
            }
        })
    }

    private fun loadDefaultData() {
        articles.clear()
        articleAdapter.setData(articles)
        page = 1
        isDefault = true
        isLast = false
        getArticles()
    }

    private fun getArticles() {
        articleViewModel.getArticles(page)
    }

    private fun setHero() {
        binding.apply {
            if (isDefault) {
                sivArticle.setImageUrl(
                    requireContext(),
                    articles[0].imageUrl.toHttps(),
                    pbHeroArticle,
                    R.drawable.img_not_available
                )
                tvHeroArticle.text = articles[0].title
                isDefault = false
            }
        }
    }

    private fun checkIsDataEmpty(articles: List<Article>) {
        binding.apply {
            if (articles.isEmpty()) {
                rvArticle.gone()
                layoutEmpty.layoutEmpty.visible()
                sivArticle.gone()
                sivBackground.gone()
                tvHeroArticle.gone()
            } else {
                rvArticle.visible()
                layoutEmpty.layoutEmpty.gone()
                sivArticle.visible()
                sivBackground.visible()
                tvHeroArticle.visible()
                setHero()
            }
        }
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