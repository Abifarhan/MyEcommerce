package com.abifarhan.myecommerce.view.ui.dashboard.ui.orders.cart.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityCartListBinding
import com.abifarhan.myecommerce.firestore.FirestoreClass
import com.abifarhan.myecommerce.model.Cart
import com.abifarhan.myecommerce.model.Product
import com.abifarhan.myecommerce.view.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_cart_list.*
import java.util.ArrayList

class CartListActivity : BaseActivity() {
    private var _binding: ActivityCartListBinding? = null
    private val binding get() = _binding!!
    private lateinit var mProductList: ArrayList<Product>

    private lateinit var mCartListItems: ArrayList<Cart>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCartListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarCartListActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        }

        binding.toolbarCartListActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
//        getCartItemsList()

        getProductList()
    }

    private fun getCartItemsList() {
//        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getCartList(this@CartListActivity)
    }

    @SuppressLint("SetTextI18n")
    fun successCartItemsList(cartList: ArrayList<Cart>) {
        hideProgressDialog()
        Log.d("ini ida","ini data cart Anda $cartList")
        for (product in mProductList) {
            for (cart in cartList) {
                if (product.product_id == cart.product_id) {
                    cart.stock_quantity = product.stockQuantity
                    if (product.stockQuantity.toInt() == 0) {
                        cart.cart_quantity = product.stockQuantity
                    }
                }
            }
        }

        mCartListItems = cartList

        if (mCartListItems.size > 0) {
            rv_cart_items_list.visibility = View.VISIBLE
            ll_checkout.visibility = View.VISIBLE
            tv_no_cart_item_found.visibility = View.GONE

            rv_cart_items_list.layoutManager = LinearLayoutManager(this@CartListActivity)
            rv_cart_items_list.setHasFixedSize(true)

            var cartListAdapter = CartItemsListAdapter(this@CartListActivity, cartList)
            rv_cart_items_list.adapter = cartListAdapter
            var subTotal: Double = 0.0

            for (item in mCartListItems) {
                val availableQuantity = item.stock_quantity.toInt()

                if (availableQuantity > 0) {
                    val price = item.price.toDouble()
                    val quantity = item.cart_quantity.toInt()

                    subTotal += (price * quantity)
                }
//                val price = item.price.toDouble()
//                val quantity = item.cart_quantity.toInt()

//                subTotal += (price * quantity)
            }

            tv_sub_total.text = "$$subTotal"
            tv_shipping_charge.text = "$10.0"

            if (subTotal > 0) {
                ll_checkout.visibility = View.VISIBLE
                val total = subTotal + 10
                tv_total_amount.text = "$$total"
            } else{
                ll_checkout.visibility = View.GONE
            }
        } else{
            rv_cart_items_list.visibility = View.GONE
            ll_checkout.visibility = View.GONE
            tv_no_cart_item_found.visibility = View.VISIBLE
        }
    }

    fun successProductsListFromFirestore(productList: ArrayList<Product>) {
        mProductList = productList

        getCartItemsList()
    }

    private fun getProductList() {
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getAllProductList(
            this@CartListActivity
        )
    }

    fun itemRemovedSuccess() {
        hideProgressDialog()

        Toast.makeText(
            this@CartListActivity,
            resources.getString(R.string.msg_item_removed_successfully),
            Toast.LENGTH_SHORT
        ).show()

        getCartItemsList()
    }
}