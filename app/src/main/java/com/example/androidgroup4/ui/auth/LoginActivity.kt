package com.example.androidgroup4.ui.auth

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivityLoginBinding
import com.example.androidgroup4.utils.isFormValid
import com.example.androidgroup4.utils.validateEmail
import com.example.androidgroup4.utils.validateNonEmpty

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    companion object {
        fun start(context: Context) {
            Intent(context, LoginActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

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
                    finish()
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

    private fun initDummyData() {
        binding.apply {
            edtEmail.setText("akbard838@gmail.com")
            edtPassword.setText("Dino123")
        }
    }

}