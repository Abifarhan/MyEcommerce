package com.abifarhan.myecommerce.view.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityUserProfileBinding
import com.abifarhan.myecommerce.model.User
import com.abifarhan.myecommerce.utils.Constants

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var userDetail: User = User()
        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            userDetail = intent.getParcelableExtra(
                Constants.EXTRA_USER_DETAILS
            )!!
        }

        binding.etFirstName.apply {
            isEnabled = false
            setText(userDetail.firstName)
        }

        binding.etLastName.apply {
            isEnabled = false
            setText(userDetail.lastName)
        }

        binding!!.etEmail.apply {
            isEnabled = false
            setText(userDetail.email)
        }
    }
}