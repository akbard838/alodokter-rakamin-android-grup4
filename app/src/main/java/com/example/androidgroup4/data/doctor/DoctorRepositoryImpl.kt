package com.example.androidgroup4.data.doctor

import com.example.androidgroup4.data.doctor.remote.DoctorDataSource
import com.example.androidgroup4.data.model.Doctor
import com.example.androidgroup4.utils.Resource
import javax.inject.Inject

class DoctorRepositoryImpl @Inject constructor(private val doctorData: DoctorDataSource) :
    DoctorRepository {

    override suspend fun getDoctors(): Resource<List<Doctor>> {
        return doctorData.getDoctors()
    }

    override suspend fun getSearchDoctors(fullName: String): Resource<List<Doctor>> {
        return doctorData.getSearchDoctors(fullName)
    }

    override suspend fun getDetailDoctor(id: Int): Resource<Doctor?> {
        return doctorData.getDetailDoctor(id)
    }


}