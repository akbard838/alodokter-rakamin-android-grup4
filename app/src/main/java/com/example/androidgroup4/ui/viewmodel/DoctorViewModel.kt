package com.example.androidgroup4.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidgroup4.data.doctor.DoctorRepository
import com.example.androidgroup4.data.model.Doctor
import com.example.androidgroup4.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(
    private val doctorRepository: DoctorRepository
) : ViewModel() {

    private val _doctors = MutableLiveData<Resource<List<Doctor>>>()
    val doctors: LiveData<Resource<List<Doctor>>> by lazy { _doctors }

    init {
        _doctors.value = Resource.init()
    }

    fun getDoctors() {
        viewModelScope.launch {
            _doctors.value = Resource.loading()
            val data = doctorRepository.getDoctors()
            _doctors.value = data
        }
    }
}