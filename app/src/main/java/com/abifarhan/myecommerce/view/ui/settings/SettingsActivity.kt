package com.abifarhan.myecommerce.view.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivitySettingsBinding
import com.abifarhan.myecommerce.view.ui.base.BaseActivity

class SettingsActivity : BaseActivity() {
    private var _binding: ActivitySettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
//        actionBar?.hide()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarSettingsActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(
                R.drawable.ic_baseline_arrow_back_ios_24
            )
        }

        binding.toolbarSettingsActivity.
                setNavigationOnClickListener {
                    onBackPressed()
                }
    }
}