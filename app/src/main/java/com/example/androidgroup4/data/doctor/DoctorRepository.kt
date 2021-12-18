package com.example.androidgroup4.data.doctor

import com.example.androidgroup4.data.model.Doctor
import com.example.androidgroup4.utils.Resource

interface DoctorRepository {

    suspend fun getDoctors(page: Int): Resource<List<Doctor>>
    suspend fun getSearchDoctors(fullName: String): Resource<List<Doctor>>
    suspend fun getDetailDoctor(id: Int): Resource<Doctor?>

}