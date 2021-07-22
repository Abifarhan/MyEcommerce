package com.abifarhan.myecommerce.view.ui.dashboard.ui.orders.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityMyOrderDetailsBinding
import com.abifarhan.myecommerce.model.Order
import com.abifarhan.myecommerce.utils.Constants

class MyOrderDetailsActivity : AppCompatActivity() {

    private var _binding: ActivityMyOrderDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMyOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        val myOderDetails: Order
        if (intent.hasExtra(Constants.EXTRA_MY_ORDER_DETAILS)){
            myOderDetails = intent.getParcelableExtra<Order>(
                Constants.EXTRA_MY_ORDER_DETAILS
            )!!
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarMyOrderDetailsActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(
                R.drawable.ic_baseline_arrow_back_ios_24
            )
        }

        binding.toolbarMyOrderDetailsActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}