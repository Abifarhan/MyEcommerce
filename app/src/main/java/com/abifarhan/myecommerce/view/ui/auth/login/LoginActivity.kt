package com.abifarhan.myecommerce.view.ui.auth.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}