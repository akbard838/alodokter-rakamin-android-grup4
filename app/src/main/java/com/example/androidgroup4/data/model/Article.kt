package com.example.androidgroup4.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title: String,
    val description: String,
    val category: String,
    val image: String
) : Parcelable
