package com.example.androidgroup4.ui.splash

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.ui.MainActivity
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivitySplashScreenBinding
import com.example.androidgroup4.ui.onboarding.OnBoardingActivity
import com.example.androidgroup4.utils.constant.PreferenceKeys
import com.example.androidgroup4.utils.getAppSharedPreference

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding =
        ActivitySplashScreenBinding::inflate

    override fun initIntent() {
    }

    override fun initUI() {
    }

    override fun initAction() {
    }

    override fun initProcess() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (getAppSharedPreference(this).getBoolean(PreferenceKeys.FIRST_TIME_LAUNCH, true))
                OnBoardingActivity.start(this)
            else
                MainActivity.start(this)
            finish()
        }, 2000)
    }

    override fun initObservable() {
    }

}