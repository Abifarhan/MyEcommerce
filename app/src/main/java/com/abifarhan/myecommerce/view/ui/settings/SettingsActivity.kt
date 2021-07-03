package com.abifarhan.myecommerce.view.ui.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivitySettingsBinding
import com.abifarhan.myecommerce.firestore.FirestoreClass
import com.abifarhan.myecommerce.model.User
import com.abifarhan.myecommerce.utils.Constants
import com.abifarhan.myecommerce.utils.GlideLoader
import com.abifarhan.myecommerce.view.ui.auth.login.LoginActivity
import com.abifarhan.myecommerce.view.ui.base.BaseActivity
import com.abifarhan.myecommerce.view.ui.profile.UserProfileActivity
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : BaseActivity(), View.OnClickListener {
    private var _binding: ActivitySettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mUserDetails: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
//        actionBar?.hide()
        binding.btnLogout.setOnClickListener(this)
        binding.tvEdit.setOnClickListener(this)
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

        binding.toolbarSettingsActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun getUserDetails() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getUserDetails(this@SettingsActivity)
    }

    fun userDetailsSuccess(user: User) {
        mUserDetails = user
        hideProgressDialog()

        GlideLoader(this@SettingsActivity)
            .loadUserPicture(user.image, binding.ivUserPhoto)
        Log.d("image", "This is your image ${user}")

        binding.tvName.text = "${user.firstName} ${user.lastName}"
        binding.tvGender.text = user.gender
        binding.tvEmail.text = user.email
        binding.tvMobileNumber.text = "${user.mobile}"
    }

    override fun onResume() {
        super.onResume()
        getUserDetails()
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.tv_edit -> {
                val intent = Intent(this@SettingsActivity, UserProfileActivity::class.java)
                intent.putExtra(Constants.EXTRA_USER_DETAILS,
                mUserDetails)
                startActivity(intent)
            }

            R.id.btn_logout -> {
                FirebaseAuth.getInstance().signOut()

                val intent = Intent(
                    this@SettingsActivity,
                    LoginActivity::class.java
                )
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }

    }
}