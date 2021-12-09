package com.example.androidgroup4.ui.onboarding

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.example.androidgroup4.ui.MainActivity
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivityOnBoardingBinding
import com.example.androidgroup4.ui.adapter.PagerAdapter

class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>() {

    companion object {
        fun start(context: Context) {
            Intent(context, OnBoardingActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    private var itemList = ArrayList<OnBoardingData>()

    override val bindingInflater: (LayoutInflater) -> ViewBinding = ActivityOnBoardingBinding::inflate

    override fun initIntent() {
    }

    override fun initUI() {
        setupViewPager()
    }

    override fun initAction() {
        binding.btnNext.setOnClickListener {
            val currentItemPosition = binding.vpOnBoarding.currentItem
            if (currentItemPosition == itemList.size -1) {
                MainActivity.start(this)
            }
            binding.vpOnBoarding.setCurrentItem(currentItemPosition + 1, true)
        }
        binding.btnSkip.setOnClickListener {
            MainActivity.start(this)
        }
    }

    override fun initProcess() {
    }

    override fun initObservable() {
    }

    private fun setupViewPager() {
        itemList = getItem()
        binding.vpOnBoarding.adapter = PagerAdapter(itemList)
        binding.indicator.setViewPager2(binding.vpOnBoarding)
        binding.vpOnBoarding.registerOnPageChangeCallback(pageChangeCallback)

    }

    private var pageChangeCallback: ViewPager2.OnPageChangeCallback = object: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (position == itemList.size - 1) binding.btnNext.text = getString(R.string.label_start)
            else binding.btnNext.text = getString(R.string.button_next)
        }
    }

    private fun getItem(): ArrayList<OnBoardingData> {
        val items = ArrayList<OnBoardingData>()
        items.add(
            OnBoardingData(
                getString(R.string.title_on_boarding_1),
                getString(R.string.message_on_boarding_1),
                R.drawable.ic_welcome
            )
        )
        items.add(
            OnBoardingData(
                getString(R.string.title_on_boarding_2),
                getString(R.string.message_on_boarding_2),
                R.drawable.ic_publish_article
            )
        )
        items.add(
            OnBoardingData(
                getString(R.string.title_on_boarding_3),
                getString(R.string.message_on_boarding_3),
                R.drawable.ic_consultation
            )
        )

        return items
    }

}