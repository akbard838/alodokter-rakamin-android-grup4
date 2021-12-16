package com.example.androidgroup4.data.user.model.request

data class UserRequest(
    val email: String,
    val name: String,
    val address: String? = "-",
    val idCardNumber: String? = "-",
    val birthDate: String? = "-",
    val gender: String
)
