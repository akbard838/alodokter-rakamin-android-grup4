package com.example.androidgroup4.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val birthDate: String,
    val gender: String,
    val ktpNumber: String,
    val address: String
) : Parcelable
