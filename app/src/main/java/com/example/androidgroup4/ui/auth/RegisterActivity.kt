package com.example.androidgroup4.ui.auth

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivityRegisterBinding
import com.example.androidgroup4.utils.*

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    companion object {
        fun start(context: Context) {
            Intent(context, RegisterActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    override val bindingInflater: (LayoutInflater) -> ViewBinding = ActivityRegisterBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        setupToolbar(binding.toolbarMain.toolbar, true, emptyString())
    }

    override fun initAction() {
        binding.apply {
            btnRegister.setOnClickListener {
                tilEmail.validateEmail()
                tilPassword.validatePassword()
                tilConfirmPassword.validateConfirmPassword(edtPassword.text.toString())
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