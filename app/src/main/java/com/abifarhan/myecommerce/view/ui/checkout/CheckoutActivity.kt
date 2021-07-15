package com.abifarhan.myecommerce.view.ui.checkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityCheckoutBinding
import com.abifarhan.myecommerce.firestore.FirestoreClass
import com.abifarhan.myecommerce.model.Address
import com.abifarhan.myecommerce.model.Cart
import com.abifarhan.myecommerce.model.Product
import com.abifarhan.myecommerce.utils.Constants
import com.abifarhan.myecommerce.view.ui.base.BaseActivity
import kotlin.collections.ArrayList

class CheckoutActivity : BaseActivity() {


    private var _binding: ActivityCheckoutBinding? = null
    private val binding get() = _binding!!

    private var mAddressDetails: Address? = null
    private lateinit var mProductList: ArrayList<Product>

    private lateinit var mCartItemsList: ArrayList<Cart>

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
        mCartItemsList = cartList
    }
}