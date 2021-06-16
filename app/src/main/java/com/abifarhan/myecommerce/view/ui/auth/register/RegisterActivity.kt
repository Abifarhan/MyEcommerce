package com.abifarhan.myecommerce.view.ui.auth.register

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityRegisterBinding
import com.abifarhan.myecommerce.view.ui.base.BaseActivity

class RegisterActivity : BaseActivity() {
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()

        binding?.btnRegister?.setOnClickListener {
            validateRegisterDetails()
        }
    }

    private fun validateRegisterDetails() {
        TODO("Not yet implemented")
    }

    private fun setupActionBar() {
      setSupportActionBar(binding?.toolbarRegisterActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        }

        binding!!.toolbarRegisterActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}