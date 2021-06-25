package com.abifarhan.myecommerce.view.ui.profile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityUserProfileBinding
import com.abifarhan.myecommerce.model.User
import com.abifarhan.myecommerce.utils.Constants
import com.abifarhan.myecommerce.utils.GlideLoader
import com.abifarhan.myecommerce.view.ui.base.BaseActivity
import java.io.IOException
import java.util.jar.Manifest

class UserProfileActivity : BaseActivity(), View.OnClickListener {
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

        binding.ivUserPhoto.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this@UserProfileActivity)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.iv_user_photo -> {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
//                        showErrorSnackBar("You already have the storage permission.", false)
                        Constants.showImageChooser(
                            this@UserProfileActivity
                        )
                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }

                R.id.btn_submit ->{
                    if (validateUserProfileDetails()) {
                        showErrorSnackBar("Your details are valid. You can update them.",false)
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode ==
            Constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
//                showErrorSnackBar("The storage permission is granted.", false)
                Constants.showImageChooser(this@UserProfileActivity)
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    try {
                        val selectedImageFileUri = data.data!!

//                        binding.ivUserPhoto.setImageURI(
//                            Uri.parse(
//                                selectedImageFileUri.toString()
//                            )
//                        )

                        GlideLoader(this@UserProfileActivity)
                            .loadUserPicture(
                                selectedImageFileUri,
                                binding.ivUserPhoto
                            )
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this@UserProfileActivity,
                            resources.getString(R.string.image_selection_failed),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("Request Cancelled", "Image selection cancelled")
        }
    }

    private fun validateUserProfileDetails(): Boolean{
        return when{
            TextUtils.isEmpty(binding.etMobileNumber.text.toString().trim() {it <= ' '}) ->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_mobile_number), true)
                false
            }

            else -> {
                true
            }
        }
    }
}