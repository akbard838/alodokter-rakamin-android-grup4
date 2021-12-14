package com.example.androidgroup4.data.user.model.response

import com.example.androidgroup4.data.model.User

data class UserResponse(
	val id: Int,
	val IdCardNumber: String,
	val fullname: String,
	val gender: String,
	val email: String,
	val BirthDate: String,
	val address: String
) {
	fun toUser(): User{
		return User(
			id = id,
			name = fullname,
			email = email,
			birthDate = BirthDate,
			gender = gender,
			idCardNumber = IdCardNumber,
			address = address
		)
	}
}
