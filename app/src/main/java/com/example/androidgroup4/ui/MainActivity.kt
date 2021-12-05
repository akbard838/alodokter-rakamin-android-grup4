package com.example.androidgroup4.ui

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivityMainBinding
import com.example.androidgroup4.ui.auth.LoginActivity
import com.example.androidgroup4.ui.profile.EditProfileActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding = ActivityMainBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {

    }

    override fun initAction() {
        binding.tvHello.setOnClickListener {
            EditProfileActivity.start(this)
        }

    }

    override fun initProcess() {

    }

    override fun initObservable() {

    }

}