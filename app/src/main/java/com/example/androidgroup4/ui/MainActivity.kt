package com.example.androidgroup4.ui

import android.content.Intent
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivityMainBinding
import com.example.androidgroup4.ui.auth.LoginActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding = ActivityMainBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {

    }

    override fun initAction() {
        binding.tvHello.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    override fun initProcess() {

    }

    override fun initObservable() {

    }

}