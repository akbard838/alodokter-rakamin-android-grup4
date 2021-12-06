package com.example.androidgroup4.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val imageUrl: String,
    val name: String,
    val address: String,
    val dummyDistance: String,
    val latitude: Float? = 0F,
    val longitude: Float? = 0F
): Parcelable