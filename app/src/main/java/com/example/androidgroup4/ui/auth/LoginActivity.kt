package com.example.androidgroup4.ui.auth

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.data.source.remote.network.ApiResponse
import com.example.androidgroup4.data.source.remote.response.UserResponse
import com.example.androidgroup4.databinding.ActivityLoginBinding
import com.example.androidgroup4.ui.UserViewModel
import com.example.androidgroup4.ui.main.MainActivity
import com.example.androidgroup4.utils.*
import com.example.androidgroup4.utils.constant.PreferenceKeys.IS_LOGIN
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    companion object {
        fun start(context: Context) {
            Intent(context, LoginActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    private val userViewModel: UserViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding = ActivityLoginBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        setupToolbar(binding.toolbarMain.toolbar, true, getString(R.string.label_login))
        initDummyData()
    }

    override fun initAction() {
        binding.apply {
            btnForgotPassword.setOnClickListener {
                ResetPasswordActivity.start(this@LoginActivity)
            }

            btnLogin.setOnClickListener {
                tilEmail.validateEmail()
                tilPassword.validateNonEmpty()

                isFormValid(listOf(tilEmail, tilPassword)) {
                    postLogin()
                }
            }

            btnRegister.setOnClickListener {
                RegisterActivity.start(this@LoginActivity)
            }
        }
    }

    override fun initProcess() {

    }

    override fun initObservable() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    private fun postLogin() {
        lifecycleScope.launchWhenStarted {
            userViewModel.postLogin(
                binding.edtEmail.text.toString(),
                binding.edtPassword.text.toString()
            ).collect {
                when (it) {
                    is ApiResponse.Success -> {
                        hideLoading()
                        if (isEmailAndPasswordVerified(it.data)){
                            getAppPreferenceEditor(this@LoginActivity).putBoolean(IS_LOGIN, true).commit()
                            MainActivity.start(this@LoginActivity)
                            finish()
                        }
                    }
                    is ApiResponse.Failure -> {
                        hideLoading()
                        showToast(this@LoginActivity, it.message)
                    }
                    is ApiResponse.Loading -> {
                        showLoading()
                    }
                }
            }
        }
    }

    private fun isEmailAndPasswordVerified(user: List<UserResponse>?): Boolean {
        return if (user.isNullOrEmpty()) {
            binding.tilEmail.error = "Email tidak terdaftar"
            false
        } else {
            binding.tilEmail.isErrorEnabled = false
            if (user[0].email != binding.edtEmail.text.toString()) {
                binding.tilEmail.error = "Email tidak terdaftar"
                false
            } else {
                binding.tilEmail.isErrorEnabled = false
                if (user[0].password != binding.edtPassword.text.toString()) {
                    binding.tilPassword.error = "Password tidak sesuai"
                    false
                } else {
                    binding.tilPassword.isErrorEnabled = false
                    true
                }
            }
        }
    }

    private fun initDummyData() {
        binding.apply {
            edtEmail.setText("akbard838@gmail.com")
            edtPassword.setText("Dino123")
        }
    }

}