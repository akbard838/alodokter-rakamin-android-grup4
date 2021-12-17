package com.example.androidgroup4.data.doctor.model

import com.example.androidgroup4.data.model.Doctor
import com.example.androidgroup4.data.model.Location
import com.example.androidgroup4.data.model.Schedule
import com.example.androidgroup4.utils.emptyString

data class DoctorResponse(
	val id: Int?,
	val avatar_url: String?,
	val specialist: String?,
	val profile: String?,
	val fullname: String?,
	val practiceLocation: String?
) {
	fun toDoctor(): Doctor {
		return Doctor(
			id = id ?: 0,
			imageUrl = avatar_url ?: emptyString(),
			name = fullname ?: emptyString(),
			specialist = specialist ?: emptyString(),
			yoe = (0..10).random(),
			profile = profile?: emptyString(),
			location = Location(
				imageUrl = "https://mayapadahospital.com/images/news/Mayapada%20Hospital%20Surabaya.jpg",
				name = "Rumah Sakit Surabaya",
				address = "Jl. Nginden Intan Barat B, Nginden Jangungan, Kec. Sukolilo, Kota SBY, Jawa Timur 60118",
				dummyDistance = "0.2 KM dari anda",
				latitude = 0F,
				longitude = 0F
			),
			schedules = listOf(
				Schedule("27 Desember 2021", "13.00 - 15.00", "16.00-18.00"),
				Schedule("28 Desember 2021", "13.00 - 15.00", "16.00-18.00"),
				Schedule("29 Desember 2021", "13.00 - 15.00", "16.00-18.00"),
				Schedule("30 Desember 2021", "13.00 - 15.00", "16.00-18.00"),
				Schedule("31 Desember 2021", "13.00 - 15.00", "16.00-18.00"),
			)
		)
	}
}
