package com.abifarhan.myecommerce.view.ui.dashboard.ui.product.addproduct

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityAddProductBinding
import com.abifarhan.myecommerce.view.ui.base.BaseActivity

class AddProductActivity : BaseActivity() {

    private var _binding: ActivityAddProductBinding? = null
    private val binding get() = _binding!!
    private var mSelectedImageFileUri: Uri? = null
    private var mProductImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
    }

    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarAddProductActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        }

        binding.toolbarAddProductActivity.setNavigationOnClickListener { onBackPressed() }
    }
}