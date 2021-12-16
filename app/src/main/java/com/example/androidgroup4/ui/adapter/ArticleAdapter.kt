package com.example.androidgroup4.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidgroup4.data.model.Article
import com.example.androidgroup4.R
import com.example.androidgroup4.databinding.ItemArticleBinding
import com.example.androidgroup4.utils.setImageUrl
import com.example.androidgroup4.utils.toHttps

class ArticleAdapter: RecyclerView.Adapter<ArticleAdapter.ListViewHolder>() {

    private var listData = ArrayList<Article>()
    var onItemClick: ((Article) -> Unit)? = null

    fun setData(newListData: List<Article>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder = ListViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemArticleBinding.bind(itemView)
        fun bind(data: Article) {
            with(binding) {
                tvTitle.text = data.title
                tvDescription.text = data.description
                ivArticle.setImageUrl(itemView.context, data.imageUrl.toHttps(), R.drawable.img_not_available)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }

    }

}