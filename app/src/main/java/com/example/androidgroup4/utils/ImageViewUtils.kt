package com.example.androidgroup4.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

fun ImageView.setImageUrl(context: Context, imageUrl: String, progressBar: ProgressBar, errorResourceId: Int) {
    val options = RequestOptions()
        .error(errorResourceId)

    progressBar.visible()

    Glide.with(context)
        .load(imageUrl.toHttps())
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.gone()
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.gone()
                return false
            }
        })
        .apply(options)
        .into(this)
}

fun ImageView.setImageUrl(c: Context, imageUrl: String, errorResourceId: Int) {
    val options = RequestOptions()
        .centerCrop()
        .error(errorResourceId)

    Glide.with(c)
        .load(imageUrl.toHttps())
        .apply(options)
        .into(this)
}