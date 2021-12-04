package com.example.androidgroup4.ui.profile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivityProfileBinding

class ProfileActivity : BaseActivity<ActivityProfileBinding>() {

    companion object {
        fun start(context: Context) {
            Intent(context, ProfileActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    override val bindingInflater: (LayoutInflater) -> ViewBinding = ActivityProfileBinding::inflate

    override fun initIntent() {

    }

    override fun initUI() {
        setupToolbar(binding.toolbarMain.toolbar, true, getString(R.string.label_profile))
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