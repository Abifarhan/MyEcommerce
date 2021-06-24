package com.abifarhan.myecommerce.view.ui.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityMainBinding
import com.abifarhan.myecommerce.utils.Constants

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
//    private val binding = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)!!

        val sharedPreferences =
            getSharedPreferences(Constants.MYSHOPPAL_PREFERENCES,
            Context.MODE_PRIVATE)

        val username = sharedPreferences.getString(
            Constants.LOGGED_IN_USERNAME,"")

        binding!!.priview.text = "This is your name $username"
    }
}