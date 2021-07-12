package com.abifarhan.myecommerce.view.ui.dashboard.ui.product.detailproduct

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityProductDetailBinding
import com.abifarhan.myecommerce.firestore.FirestoreClass
import com.abifarhan.myecommerce.model.Cart
import com.abifarhan.myecommerce.model.Product
import com.abifarhan.myecommerce.utils.Constants
import com.abifarhan.myecommerce.utils.GlideLoader
import com.abifarhan.myecommerce.view.ui.base.BaseActivity
import com.abifarhan.myecommerce.view.ui.dashboard.ui.orders.cart.list.CartListActivity
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : BaseActivity(), View.OnClickListener {
    private var _binding: ActivityProductDetailBinding? = null
    private val binding get() = _binding!!
    private var mProductId: String = ""
    private lateinit var mProductDetail: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        if (intent.hasExtra(Constants.EXTRA_PRODUCT_ID)) {
            mProductId = intent.getStringExtra(Constants.EXTRA_PRODUCT_ID)!!
            Toast.makeText(this, "ini id produk Anda $mProductId", Toast.LENGTH_SHORT).show()
        }

        var productOwnerId: String = ""
        if (intent.hasExtra(Constants.EXTRA_PRODUCT_OWNER_ID)) {
            productOwnerId =
                intent.getStringExtra(Constants.EXTRA_PRODUCT_OWNER_ID)!!
        }
//        binding.btnPublish.setOnClickListener {
//            Toast.makeText(this, "Anda mulai klik", Toast.LENGTH_SHORT).show()
//        }

        if (FirestoreClass().getCurrentUserID() == productOwnerId) {
            binding.btnAddToCart.visibility = View.GONE
            binding.btnGoToCart.visibility = View.GONE
        } else {
            binding.btnAddToCart.visibility = View.VISIBLE
        }

        binding.btnAddToCart.setOnClickListener(this)
        binding.btnGoToCart.setOnClickListener(this)
        getProductDetails()
    }

    private fun getProductDetails() {
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getProductDetails(
            this,
            mProductId
        )
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarProductDetailsActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        }

        binding.toolbarProductDetailsActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun productDetailsSuccess(product: Product) {
        mProductDetail = product
//        hideProgressDialog()

        GlideLoader(this@ProductDetailActivity).loadProductPicture(
            product.image,
            binding.ivProductDetailImage
        )

        binding.tvProductDetailsTitle.text = product.title
        binding.tvProductDetailsPrice.text = "$${product.price}"
        binding.tvProductDetailsDescription.text = product.description
        binding.tvProductDetailsAvailableQuantity.text = product.stockQuantity

        if (FirestoreClass().getCurrentUserID() == product.userId) {
            hideProgressDialog()
        } else {
            FirestoreClass().checkIfItemExistInCart(this,
            mProductId)
        }

        if (product.stockQuantity.toInt() == 0) {
            hideProgressDialog()

            btn_add_to_cart.visibility = View.GONE

            tv_product_details_available_quantity.text =
                resources.getString(R.string.lbl_out_of_stock)

            tv_product_details_available_quantity.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorSnackBarError
                )
            )
        }
        else{
            if (FirestoreClass().getCurrentUserID() == product.userId) {
                hideProgressDialog()
            }else{
                FirestoreClass().checkIfItemExistInCart(
                    this,mProductId
                )
            }
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_add_to_cart -> {
                    addToCart()
                }

                R.id.btn_go_to_cart -> {
                    startActivity(Intent(
                        this@ProductDetailActivity,
                        CartListActivity::class.java
                    ))
                }
            }
        }
    }

    private fun addToCart() {
        val addToCart = Cart(
            FirestoreClass().getCurrentUserID(),
            mProductId,
            mProductDetail.title,
            mProductDetail.price,
            mProductDetail.image,
            Constants.DEFAULT_CART_QUANTITY
        )
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().addCartItems(this@ProductDetailActivity, addToCart)
    }

    fun addToCartSuccess() {
        hideProgressDialog()

        Toast.makeText(
            this@ProductDetailActivity,
            resources.getString(R.string.success_message_item_added_to_cart),
            Toast.LENGTH_SHORT
        ).show()

        binding.btnAddToCart.visibility = View.GONE
        binding.btnGoToCart.visibility = View.VISIBLE
    }

    fun productExistInCart() {
        hideProgressDialog()

        binding.btnAddToCart.visibility = View.GONE
        binding.btnGoToCart.visibility = View.VISIBLE
    }
}