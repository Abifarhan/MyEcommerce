package com.abifarhan.myecommerce.view.ui.auth.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
}