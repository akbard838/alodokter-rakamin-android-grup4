package com.example.androidgroup4.ui.auth

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivityLoginBinding
import com.example.androidgroup4.ui.main.MainActivity
import com.example.androidgroup4.ui.viewmodel.UserViewModel
import com.example.androidgroup4.utils.*
import com.example.androidgroup4.utils.constant.PreferenceKeys
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

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
    }

    override fun initAction() {
        binding.apply {
            btnForgotPassword.setOnClickListener {
                ForgotPasswordActivity.start(this@LoginActivity)
            }

            btnLogin.setOnClickListener {
                tilEmail.validateEmail()
                tilPassword.validateNonEmpty()

                isFormValid(listOf(tilEmail, tilPassword)) {
                    userViewModel.postLogin(
                        binding.edtEmail.text.toString(),
                        binding.edtPassword.text.toString()
                    )
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
        userViewModel.login.observe(this, {
            when (it) {
                is Resource.Loading -> {
                    showLoading()
                    binding.tvError.gone()
                }
                is Resource.Success -> {
                    hideLoading()
                    val user = Gson().toJson(it.data)
                    getAppPreferenceEditor(this).putString(PreferenceKeys.USER, user).apply()
                    getAppPreferenceEditor(this).putBoolean(PreferenceKeys.IS_LOGIN, true).apply()
                    MainActivity.start(this)
                    finish()
                }
                is Resource.Error -> {
                    hideLoading()
                    if (it.apiError.message == getString(R.string.error_email_or_password)) {
                        binding.tvError.text = getString(R.string.error_email_or_password_id)
                        binding.tvError.visible()
                    } else {
                        showToast(this, it.apiError.message)
                    }
                }
                else -> {}
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

}