package com.example.androidgroup4.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidgroup4.data.model.Model
import com.example.androidgroup4.data.user.UserRepository
import com.example.androidgroup4.data.user.model.request.RegisterRequest
import com.example.androidgroup4.utils.Resource
import com.example.androidgroup4.utils.Resource.Companion.init
import com.example.androidgroup4.utils.Resource.Companion.loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _login = MutableLiveData<Resource<String>>()
    val login: LiveData<Resource<String>> by lazy { _login }

    private val _register = MutableLiveData<Resource<Model>>()
    val register: LiveData<Resource<Model>> by lazy { _register }

    init {
        _login.value = init()
        _register.value = init()
    }

    fun postLogin(email: String, password: String) {
        viewModelScope.launch {
            _login.value = loading()
            val data = userRepository.postLogin(email, password)
            _login.value = data
        }
    }

    fun postRegister(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            _register.value = loading()
            val data = userRepository.postRegister(registerRequest)
            _register.value = data
        }
    }
}