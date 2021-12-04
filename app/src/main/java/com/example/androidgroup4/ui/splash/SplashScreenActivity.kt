package com.example.androidgroup4.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivitySplashScreenBinding
import com.example.androidgroup4.ui.MainActivity
import com.example.androidgroup4.ui.profile.ProfileActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding = ActivitySplashScreenBinding::inflate

    override fun initIntent() {
    }

    override fun initUI() {
    }

    override fun initAction() {
    }

    override fun initProcess() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }

    override fun initObservable() {
    }

}