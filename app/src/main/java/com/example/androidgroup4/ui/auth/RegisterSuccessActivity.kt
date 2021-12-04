package com.example.androidgroup4.ui.auth

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivityRegisterSuccessBinding

class RegisterSuccessActivity: BaseActivity<ActivityRegisterSuccessBinding>() {

    companion object {
        fun start(context: Context) {
            Intent(context, RegisterSuccessActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    override val bindingInflater: (LayoutInflater) -> ViewBinding = ActivityRegisterSuccessBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
    }

    override fun initAction() {
        binding.apply {
            btnLogin.setOnClickListener {
                LoginActivity.start(this@RegisterSuccessActivity)
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