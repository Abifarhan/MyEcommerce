package com.abifarhan.myecommerce.view.ui.auth.forgotpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityForgotPasswordBinding
import com.abifarhan.myecommerce.view.ui.base.BaseActivity

class ForgotPasswordActivity : BaseActivity() {

    var _binding : ActivityForgotPasswordBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setupActionBar
//
//        binding.btnS

   }
}