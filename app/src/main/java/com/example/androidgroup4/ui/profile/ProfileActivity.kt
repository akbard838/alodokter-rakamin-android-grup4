package com.example.androidgroup4.ui.profile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.data.model.Profile
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
            fabChangePhoto.setOnClickListener {

            }
            fabEditProfile.setOnClickListener {
                val profile = Profile(
                    getString(R.string.sample_name),
                    getString(R.string.sample_date_of_birth),
                    getString(R.string.sample_id_card_number),
                    getString(R.string.sample_address)
                )
                EditProfileActivity.start(this@ProfileActivity, profile)
            }
            bChangePwd.setOnClickListener {

            }
            bLogout.setOnClickListener {

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