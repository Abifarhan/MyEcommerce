package com.abifarhan.myecommerce.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val userId: String = "",
    val userName: String = "",
    val title: String = "",
    val price: String = "",
    val description: String = "",
    val stockQuantity: String = "",
    val image: String = "",
    var product_id: String = "",
): Parcelable