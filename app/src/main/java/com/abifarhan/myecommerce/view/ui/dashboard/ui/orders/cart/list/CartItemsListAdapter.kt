package com.abifarhan.myecommerce.view.ui.dashboard.ui.orders.cart.list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.model.Cart
import com.abifarhan.myecommerce.utils.GlideLoader
import kotlinx.android.synthetic.main.item_cart_layout.view.*

class CartItemsListAdapter(
    private val context: Context,
    private var list: ArrayList<Cart>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_cart_layout,
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            GlideLoader(context).loadProductPicture(
                model.image,
                holder.itemView.iv_cart_item_image
            )

            holder.itemView.tv_cart_item_title.text =
                model.title

            holder.itemView.tv_cart_item_price.text =
                "$${model.price}"

            holder.itemView.tv_cart_quantity.text =
                model.cart_quantity
        }
    }

    override fun getItemCount(): Int = list.size


    private class MyViewHolder(view: View)
        : RecyclerView.ViewHolder(view)
}