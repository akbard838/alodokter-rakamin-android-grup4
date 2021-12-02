package com.example.androidgroup4.base

import androidx.appcompat.widget.Toolbar

interface BaseView {

    fun setupToolbar(toolbar: Toolbar?, isChild : Boolean, title: String,)

}