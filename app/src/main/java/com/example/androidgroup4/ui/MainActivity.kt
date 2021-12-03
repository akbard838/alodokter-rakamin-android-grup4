package com.example.androidgroup4.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidgroup4.R
import com.example.androidgroup4.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    // base activity for fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.frame_container, HomeFragment(), HomeFragment::class.java.simpleName).commit()
    }


}