package com.example.androidgroup4.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val description: String
) : Parcelable
