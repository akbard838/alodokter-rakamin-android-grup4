package com.example.androidgroup4.data.user.model.request

data class RegisterRequest (
   val name: String,
   val email: String,
   val password: String,
   val address: String? = "-",
   val idCardNumber: String? = "-",
   val birthDate: String? = "-",
   val gender: String
)