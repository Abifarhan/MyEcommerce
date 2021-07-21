package com.abifarhan.myecommerce.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    val userId: String = "",
    val items: ArrayList<Cart> = ArrayList(),
    val address: Address = Address(),
    val title: String = "",
    val image: String = "",
    val sub_total_amount: String = "",
    val shipping_charge: String = "",
    val total_amount: String = "",
    var id: String = ""
) : Parcelable
