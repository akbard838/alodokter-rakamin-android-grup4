package com.example.androidgroup4.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
	val createdAt: String,
	val password: String,
	val deletedAt: String,
	val user_id: String,
	val no_ktp: Long,
	val fullname: String,
	val jenis_kelamin: String,
	val email: String,
	val tgl_lahir: String,
	val alamat: String
): Parcelable
