package com.example.androidgroup4.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidgroup4.databinding.ItemOnBoardingBinding
import com.example.androidgroup4.ui.onboarding.OnBoardingData

class PagerAdapter(private val items: ArrayList<OnBoardingData>) :
    RecyclerView.Adapter<PagerAdapter.TopViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopViewHolder {
        return TopViewHolder(
            ItemOnBoardingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TopViewHolder, position: Int) {
        val data = items[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = items.size

    class TopViewHolder(private val binding: ItemOnBoardingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: OnBoardingData) {
            binding.tvTitle.text = data.title
            binding.tvMessage.text = data.message
            binding.imgOnBoarding.setImageResource(data.image)
        }
    }

}