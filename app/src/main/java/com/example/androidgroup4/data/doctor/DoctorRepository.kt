package com.example.androidgroup4.data.doctor

import com.example.androidgroup4.data.model.Doctor
import com.example.androidgroup4.utils.Resource

interface DoctorRepository {

    suspend fun getDoctors(): Resource<List<Doctor>>

}