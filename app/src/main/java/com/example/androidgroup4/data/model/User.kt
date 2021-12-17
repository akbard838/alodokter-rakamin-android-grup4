package com.example.androidgroup4.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val birthDate: String,
    val gender: String,
    val idCardNumber: String,
    val address: String,
) : Parcelable
