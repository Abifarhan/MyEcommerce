package com.abifarhan.myecommerce.view.ui.profile

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityUserProfileBinding
import com.abifarhan.myecommerce.model.User
import com.abifarhan.myecommerce.utils.Constants
import com.abifarhan.myecommerce.view.ui.base.BaseActivity
import java.util.jar.Manifest

class UserProfileActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var userDetail: User = User()
        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS))
        {
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
                        showErrorSnackBar("You already have the storage permission.", false)
                    }
                    else{
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
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
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showErrorSnackBar("The storage permission is granted.", false)
            }else{
                Toast.makeText(
                    this,
                    resources.getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}