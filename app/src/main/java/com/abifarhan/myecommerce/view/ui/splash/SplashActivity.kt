package com.abifarhan.myecommerce.view.ui.splash

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivitySplashBinding
import com.abifarhan.myecommerce.view.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private var _binding : ActivitySplashBinding? = null
    private val binding get() = _binding!!

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed(
            {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            },3000
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}