package com.example.androidgroup4.data.model

data class Doctor(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val specialist: String,
    val yoe: Int,
    val profile: String,
    val location: Location,
    val schedules: List<Schedule>
)