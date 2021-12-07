package com.example.androidgroup4.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidgroup4.Article
import com.example.androidgroup4.R
import com.example.androidgroup4.databinding.ArticleItemBinding

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
        LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false))

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ArticleItemBinding.bind(itemView)
        fun bind(data: Article) {
            with(binding) {
                tvTitle.text = data.title
                tvDescription.text = data.description
                tvCategory.text = data.category
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }

    }

}