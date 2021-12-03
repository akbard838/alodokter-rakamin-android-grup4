package com.example.androidgroup4.ui.profile

import android.view.LayoutInflater
import android.view.MenuItem
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivityLoginBinding
import com.example.androidgroup4.databinding.ActivityProfileBinding

class ProfileActivity : BaseActivity<ActivityProfileBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding = ActivityLoginBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        setupToolbar(binding.toolbarMain.toolbar, true, getString(R.string.label_login))
    }

    override fun initAction() {
        binding.apply {
            changePhoto.setOnClickListener {

            }
            editProfile.setOnClickListener {

            }
            changePassword.setOnClickListener {

            }
            logout.setOnClickListener {

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