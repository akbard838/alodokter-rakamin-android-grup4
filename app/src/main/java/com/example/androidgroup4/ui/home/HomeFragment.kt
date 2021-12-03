package com.example.androidgroup4.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidgroup4.Article
import com.example.androidgroup4.R
import com.example.androidgroup4.databinding.FragmentHomeBinding
import com.example.androidgroup4.ui.detail.DetailArticleFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initAction()
    }

    private fun initAction() {
        articleAdapter.onItemClick = { article ->
            val detailArticleFragment = DetailArticleFragment()
            // send data
            val mBundle = Bundle()
            mBundle.putParcelable(DetailArticleFragment.EXTRA_ARTICLE, article)
            detailArticleFragment.arguments = mBundle
            // untuk pindah fragmment
            parentFragmentManager.beginTransaction().apply {
                replace(
                    R.id.frame_container,
                    detailArticleFragment,
                    DetailArticleFragment::class.java.simpleName
                )
                addToBackStack(null)
                commit()
            }
        }
    }
    private fun initUI() {
        articleAdapter = ArticleAdapter()
        articleAdapter.setData(dummyData())
        with(binding.rvArticle) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = articleAdapter
        }
    }

    //tes
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}