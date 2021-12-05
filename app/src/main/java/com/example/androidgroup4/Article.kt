package com.example.androidgroup4

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title: String,
    val description: String,
    val category: String
) : Parcelable
