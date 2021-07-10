package com.abifarhan.myecommerce.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.abifarhan.myecommerce.R
import com.bumptech.glide.Glide
import java.io.IOException

class GlideLoader(val context: Context) {

    fun loadUserPicture(
        imageURI: Any,
        imageView: ImageView
    ) {
        try {
            Glide.with(context)
                .load(imageURI)
                .centerCrop()
                .placeholder(R.color.colorThemeGreen)
                .into(imageView)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadProductPicture(image: Any, imageView: ImageView?) {
        try {
            // Load the user image in the ImageView.
            Glide
                .with(context)
                .load(image) // Uri or URL of the image
                .centerCrop() // Scale type of the image.
                .into(imageView!!) // the view in which the image will be loaded.
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}