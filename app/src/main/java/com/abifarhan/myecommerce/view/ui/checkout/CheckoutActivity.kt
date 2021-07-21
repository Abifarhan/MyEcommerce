package com.abifarhan.myecommerce.view.ui.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityCheckoutBinding
import com.abifarhan.myecommerce.firestore.FirestoreClass
import com.abifarhan.myecommerce.model.Address
import com.abifarhan.myecommerce.model.Cart
import com.abifarhan.myecommerce.model.Order
import com.abifarhan.myecommerce.model.Product
import com.abifarhan.myecommerce.utils.Constants
import com.abifarhan.myecommerce.view.ui.base.BaseActivity
import com.abifarhan.myecommerce.view.ui.dashboard.DashBoardActivity
import com.abifarhan.myecommerce.view.ui.dashboard.ui.orders.cart.list.CartItemsListAdapter
import kotlinx.android.synthetic.main.item_cart_layout.*
import kotlin.collections.ArrayList

class CheckoutActivity : BaseActivity() {


    private var _binding: ActivityCheckoutBinding? = null
    private val binding get() = _binding!!

    private var mAddressDetails: Address? = null
    private lateinit var mProductList: ArrayList<Product>
    private lateinit var mCartItemsList: ArrayList<Cart>
    private var mSubTotal: Double = 0.0
    private var mTotalAmount: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        if (intent.hasExtra(Constants.EXTRA_SELECTED_ADDRESS)) {
            mAddressDetails = intent.getParcelableExtra(
                Constants.EXTRA_SELECTED_ADDRESS
            )!!
        }

        if (mAddressDetails != null) {
            binding.tvCheckoutAddressType.text = mAddressDetails?.type

            binding.tvCheckoutFullName.text =
                mAddressDetails?.name

            binding.tvCheckoutAddress.text =
                "${mAddressDetails!!.address}," +
                        " ${mAddressDetails!!.zipCode}"

            binding.tvCheckoutAdditionalNote.text =
                mAddressDetails?.additionalNote

            if (mAddressDetails?.otherDetails!!.isNotEmpty()) {
                binding.tvCheckoutOtherDetails.text =
                    mAddressDetails?.otherDetails
            }

            binding.tvMobileNumber.text =
                mAddressDetails?.mobileNumber
        }

        binding.btnPlaceOrder.setOnClickListener {
            placeAnOrder()
        }
        getProductList()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarCheckoutActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        }

        binding.toolbarCheckoutActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun getProductList() {
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getAllProductList(this@CheckoutActivity)
    }

    fun successProductListFromFirestore(productList: ArrayList<Product>) {
        mProductList = productList

        getCartItemsList()
    }

    private fun getCartItemsList() {
        FirestoreClass().getCartList(
            this@CheckoutActivity
        )
    }

    fun successCartItemsList(cartList: ArrayList<Cart>) {
        hideProgressDialog()
//        mCartItemsList = cartList
        for (product in mProductList) {
            for (cart in cartList) {
                if (product.product_id == cart.product_id) {
                    cart.stock_quantity = product.stockQuantity
                }
            }
        }

        mCartItemsList = cartList
        binding.rvCartListItems.layoutManager =
            LinearLayoutManager(this@CheckoutActivity)
        binding.rvCartListItems.setHasFixedSize(true)

        val cartListAdapter = CartItemsListAdapter(this,
        mCartItemsList, false)
        binding.rvCartListItems.adapter = cartListAdapter


        var subTotal: Double = 0.0

        for (item in mCartItemsList) {
            val availableQuantity = item.stock_quantity.toInt()

            if (availableQuantity > 0) {
                val price = item.price.toDouble()
                val quantity = item.cart_quantity.toInt()

                subTotal += (price * quantity)
            }
        }

        binding.tvCheckoutSubTotal.text = "$$mSubTotal"
        binding.tvCheckoutShippingCharge.text = "$10.0"
        if (mSubTotal > 0) {
            binding.llCheckoutPlaceOrder.visibility = View.VISIBLE

            val total = subTotal + 10
            binding.tvCheckoutTotalAmount.text = "$$total"
        } else {
            binding.llCheckoutPlaceOrder.visibility = View.GONE
        }
    }

    private fun placeAnOrder() {
        showProgressDialog(resources.getString(R.string.please_wait))

        val order = Order(
            FirestoreClass().getCurrentUserID(),
            mCartItemsList,
            mAddressDetails!!,
            "My order ${System.currentTimeMillis()}",
            mCartItemsList[0].image,
            mSubTotal.toString(),
            "10.0",
            mTotalAmount.toString()
        )

        FirestoreClass().placeOrder(
            this@CheckoutActivity, order
        )
    }

    fun orderPlacedSuccess() {
        hideProgressDialog()
        Toast.makeText(this@CheckoutActivity, "Your order placed successfully.", Toast.LENGTH_SHORT)
            .show()

        val intent = Intent(this@CheckoutActivity, DashBoardActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}