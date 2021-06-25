package com.abifarhan.myecommerce.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.abifarhan.myecommerce.R
import com.bumptech.glide.Glide
import java.io.IOException

class GlideLoader(val context: Context) {

    fun loadUserPicture(
        imageURI: Uri,
        imageView: ImageView
    ) {
        try {
            Glide.with(context)
                .load(Uri.parse(imageURI.toString()))
                .centerCrop()
                .placeholder(R.color.colorThemeGreen)
                .into(imageView)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}