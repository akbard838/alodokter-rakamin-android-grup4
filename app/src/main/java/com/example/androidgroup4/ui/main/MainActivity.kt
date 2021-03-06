package com.example.androidgroup4.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBinding
import com.example.androidgroup4.R
import com.example.androidgroup4.base.BaseActivity
import com.example.androidgroup4.databinding.ActivityMainBinding
import com.example.androidgroup4.utils.constant.PreferenceKeys
import com.example.androidgroup4.utils.constant.PreferenceKeys.IS_LOGIN
import com.example.androidgroup4.utils.getAppPreferenceEditor
import com.example.androidgroup4.utils.getAppSharedPreference
import com.example.androidgroup4.utils.showToast
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        fun start(context: Context) {
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(this)
            }
        }

        fun getUserLoggedInStatus(context: Context): Boolean {
            return getAppSharedPreference(context).getBoolean(IS_LOGIN, false)
        }
    }

    private var backPress: Long = 0L

    private lateinit var exitToast: Toast

    override val bindingInflater: (LayoutInflater) -> ViewBinding = ActivityMainBinding::inflate

    override fun initIntent() {
    }

    override fun initUI() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_home)

        navView.setupWithNavController(navController)
    }

    override fun initAction() {

    }

    override fun initProcess() {
        getAppPreferenceEditor(this).putBoolean(PreferenceKeys.FIRST_TIME_LAUNCH, false).apply()
    }

    override fun initObservable() {
    }

    override fun onBackPressed() {
        if (backPress + 3000 > System.currentTimeMillis()) {
            exitToast.cancel()
            super.onBackPressed()
        } else {
            if (binding.navView.selectedItemId != R.id.navigation_home) super.onBackPressed()
            else {
                exitToast = Toast.makeText(this, getString(R.string.message_tap_more_to_exit), Toast.LENGTH_LONG)
                exitToast.show()
                backPress = System.currentTimeMillis()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return false
    }

}