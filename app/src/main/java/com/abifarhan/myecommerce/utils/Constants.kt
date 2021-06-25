package com.abifarhan.myecommerce.utils

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import com.abifarhan.myecommerce.view.ui.base.BaseActivity

object Constants {

    const val USERS: String = "users"
    const val MYSHOPPAL_PREFERENCES: String = "MyShopPalPrefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"
    const val EXTRA_USER_DETAILS: String = "extra_user_details"
    const val READ_STORAGE_PERMISSION_CODE = 2
    const val PICK_IMAGE_REQUEST_CODE = 2

    const val MALE: String = "Male"
    const val FEMALE: String = "Female"

    const val MOBILE: String = "mobile"
    const val GENDER: String = "gender"

    fun showImageChooser(activity: Activity) {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        activity.startActivityForResult(galleryIntent,
        PICK_IMAGE_REQUEST_CODE)
    }
}