package com.example.androidgroup4.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Schedule(
    val date: String,
    val hourOne: String,
    val hourTwo: String
): Parcelable