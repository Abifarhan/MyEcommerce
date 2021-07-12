package com.abifarhan.myecommerce.view.ui.dashboard.ui.orders.cart.list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.firestore.FirestoreClass
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

            if (model.cart_quantity == "0") {
                holder.itemView.ib_remove_cart_item.visibility = View.GONE
                holder.itemView.ib_add_cart_item.visibility = View.GONE

                holder.itemView.tv_cart_quantity.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorSnackBarError
                    )
                )
            }else{
                holder.itemView.apply {
                    ib_remove_cart_item.visibility = View.VISIBLE
                    ib_add_cart_item.visibility = View.VISIBLE

                    tv_cart_quantity.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorSecondaryText
                        )
                    )
                }
            }

            holder.itemView.ib_delete_cart_item.setOnClickListener{

                when (context) {
                    is CartListActivity -> {
                        context.showProgressDialog(context.resources.getString(R.string.please_wait))
                    }
                }

                FirestoreClass().removeItemFromCart(context, model.id)
            }
        }
    }

    override fun getItemCount(): Int = list.size


    private class MyViewHolder(view: View)
        : RecyclerView.ViewHolder(view)
}