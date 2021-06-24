package com.abifarhan.myecommerce.view.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityUserProfileBinding

class UserProfileActivity : AppCompatActivity() {
    private var binding: ActivityUserProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
    }
}