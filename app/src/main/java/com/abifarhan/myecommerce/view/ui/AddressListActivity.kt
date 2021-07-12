package com.abifarhan.myecommerce.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityAddressListBinding

class AddressListActivity : AppCompatActivity() {
    private var _binding: ActivityAddressListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddressListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarAddressListActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        }

        binding.toolbarAddressListActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}