package com.abifarhan.myecommerce.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import com.abifarhan.myecommerce.view.ui.base.BaseActivity

object Constants {

    const val USERS: String = "users"
    const val PRODUCTS: String = "products"
    const val CART_ITEMS: String = "cart_items"
    const val ORDERS: String = "orders"
    const val STOCK_QUANTITY: String = "stockQuantity"
    const val EXTRA_MY_ORDER_DETAILS: String = "extra_MY_ORDER_DETAILS"
    const val SOLD_PRODUCTS: String = "sold_products"


    const val MYSHOPPAL_PREFERENCES: String = "MyShopPalPrefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"
    const val EXTRA_USER_DETAILS: String = "extra_user_details"
    const val READ_STORAGE_PERMISSION_CODE = 2
    const val PICK_IMAGE_REQUEST_CODE = 2

    const val EXTRA_PRODUCT_ID: String = "extra_product_id"
    const val EXTRA_PRODUCT_OWNER_ID: String = "extra_product_owner_id"

    const val MALE: String = "Male"
    const val FEMALE: String = "Female"
    const val DEFAULT_CART_QUANTITY: String = "1"

    const val MOBILE: String = "mobile"
    const val GENDER: String = "gender"
    const val IMAGE: String = "image"
    const val COMPLETE_PROFILE: String = "profileCompleted"

    const val FIRST_NAME: String = "firstName"
    const val LAST_NAME: String = "lastName"

    const val USER_ID: String = "userId"
    const val PRODUCT_ID: String = "product_id"

    const val USER_PROFILE_IMAGE: String = "User_Profile_Image"
    const val PRODUCT_IMAGE: String = "Product_Image"
    const val CART_QUANTITY: String = "cart_quantity"

    const val HOME: String = "Home"
    const val OFFICE: String = "Office"
    const val OTHER: String = "Other"

    const val ADDRESSES: String = "addresses"
    const val EXTRA_ADDRESS_DETAILS: String = "AddressDetails"

    const val EXTRA_SELECT_ADDRESS: String = "extra_select_address"
    const val ADD_ADDRESS_REQUEST_CODE: Int = 121
    const val EXTRA_SELECTED_ADDRESS: String = "extra_selected_address"

    fun showImageChooser(activity: Activity) {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        activity.startActivityForResult(galleryIntent,
        PICK_IMAGE_REQUEST_CODE)
    }

    fun getFileExtension(activity: Activity, uri: Uri?): String? {

        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(
                activity.contentResolver.getType(uri!!)
            )
    }
}