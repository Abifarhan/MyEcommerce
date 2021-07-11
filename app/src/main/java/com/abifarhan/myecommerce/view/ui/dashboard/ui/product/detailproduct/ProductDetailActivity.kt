package com.abifarhan.myecommerce.view.ui.dashboard.ui.product.detailproduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityProductDetailBinding
import com.abifarhan.myecommerce.firestore.FirestoreClass
import com.abifarhan.myecommerce.model.Product
import com.abifarhan.myecommerce.utils.Constants
import com.abifarhan.myecommerce.utils.GlideLoader
import com.abifarhan.myecommerce.view.ui.base.BaseActivity

class ProductDetailActivity : BaseActivity() {
    private var _binding: ActivityProductDetailBinding? = null
    private val binding get() = _binding!!
    private var mProductId: String = ""


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
        } else {
            binding.btnAddToCart.visibility = View.VISIBLE
        }
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
        hideProgressDialog()

        GlideLoader(this@ProductDetailActivity).loadProductPicture(
            product.image,
            binding.ivProductDetailImage
        )

        binding.tvProductDetailsTitle.text = product.title
        binding.tvProductDetailsPrice.text = "$${product.price}"
        binding.tvProductDetailsDescription.text = product.description
        binding.tvProductDetailsAvailableQuantity.text = product.stockQuantity

    }
}