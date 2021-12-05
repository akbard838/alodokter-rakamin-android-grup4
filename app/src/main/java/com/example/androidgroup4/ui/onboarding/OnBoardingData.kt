package com.example.androidgroup4.ui.onboarding

import androidx.annotation.DrawableRes

data class OnBoardingData(
    val title: String,
    val message: String,
    @DrawableRes val image: Int
)