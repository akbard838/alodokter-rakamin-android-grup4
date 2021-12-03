package com.example.androidgroup4.ui.auth

import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivityLoginBinding
import com.example.androidgroup4.ui.profile.ProfileActivity
import com.example.androidgroup4.utils.showToast
import com.example.androidgroup4.utils.validateEmail
import com.example.androidgroup4.utils.validateNonEmpty

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding = ActivityLoginBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        setupToolbar(binding.toolbarMain.toolbar, true, getString(R.string.label_login))
    }

    override fun initAction() {
        binding.apply {
            btnForgotPassword.setOnClickListener {
                startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
            }

            btnLogin.setOnClickListener {
                tilEmail.validateEmail()
                tilPassword.validateNonEmpty()
            }

            btnRegister.setOnClickListener {
                showToast(this@LoginActivity, "Register")
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

}