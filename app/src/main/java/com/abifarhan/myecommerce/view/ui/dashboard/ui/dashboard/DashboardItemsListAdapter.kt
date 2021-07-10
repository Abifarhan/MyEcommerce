package com.abifarhan.myecommerce.view.ui.dashboard.ui.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.model.Product
import com.abifarhan.myecommerce.utils.GlideLoader
import kotlinx.android.synthetic.main.item_dashboard_layout.view.*
import java.util.ArrayList

open class DashboardItemsListAdapter(
    private val context: Context,
    private var list: ArrayList<Product>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_dashboard_layout,
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {

            GlideLoader(context).loadUserPicture(
                model.image,
                holder.itemView.iv_dashboard_item_image
            )
            holder.itemView.tv_dashboard_item_title.text = model.title
            holder.itemView.tv_dashboard_item_price.text = "$${model.price}"
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
