package com.abifarhan.myecommerce.view.ui.dashboard.ui.product.addproduct

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityAddProductBinding
import com.abifarhan.myecommerce.firestore.FirestoreClass
import com.abifarhan.myecommerce.model.Product
import com.abifarhan.myecommerce.utils.Constants
import com.abifarhan.myecommerce.utils.GlideLoader
import com.abifarhan.myecommerce.view.ui.base.BaseActivity
import java.io.IOException

class AddProductActivity : BaseActivity(), View.OnClickListener {

    private var _binding: ActivityAddProductBinding? = null
    private val binding get() = _binding!!
    private var mSelectedImageFileUri: Uri? = null
    private var mProductImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        binding.ivAddUpdateProduct.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
    }

    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarAddProductActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        }

        binding.toolbarAddProductActivity.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.iv_add_update_product -> {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Constants.showImageChooser(
                            this@AddProductActivity
                        )
                    }
                    else {

                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }

                R.id.btn_submit ->{
                    if (validateProductDetails()) {
                        uploadProductImage()
                    }
                }
            }
        }
    }

    private fun uploadProductImage() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().uploadImageToCloudStorage(
            this@AddProductActivity,
            mSelectedImageFileUri,
            Constants.PRODUCT_IMAGE
        )
    }

    private fun validateProductDetails(): Boolean {
        return when{
            mSelectedImageFileUri == null ->{
                showErrorSnackBar(resources.getString(R.string.err_msg_select_product_image), true)
                false
            }

            TextUtils.isEmpty(binding.etProductTitle.text.toString().trim() {it <= ' '}) -> {
                showErrorSnackBar("Please enter product price", true)
                false
            }

            TextUtils.isEmpty(binding.etProductDescription.text.toString()
                .trim() { it <= ' '}) -> {
                    showErrorSnackBar("Please enter product description", true)
                false
                }

            TextUtils.isEmpty(binding.etProductQuantity.text.toString().trim() {it <= ' '}) -> {
                showErrorSnackBar("Please enter product quantity", true)
                false
            }
            else ->{
                true
            }
        }
    }

    fun imageUploadSuccess(imageUrl: String) {
        mProductImageURL = imageUrl
        uploadProductDetails()
    }

    private fun uploadProductDetails() {
        val username =
            this.getSharedPreferences(Constants.MYSHOPPAL_PREFERENCES,
            Context.MODE_PRIVATE)
                .getString(Constants.LOGGED_IN_USERNAME,
                "")!!

        val product = Product(
            FirestoreClass().getCurrentUserID(),
            username,
            binding.etProductTitle.text.toString().trim() {it <= ' '},
            binding.etProductPrice.text.toString().trim() {it <= ' '},
            binding.etProductDescription.text.toString().trim() {it <= ' '},
            binding.etProductQuantity.text.toString().trim() {it <= ' '},
            mProductImageURL
        )

        FirestoreClass().uploadProductDetails(
            this@AddProductActivity, product
        )
    }

    fun productUploadSuccess() {
        hideProgressDialog()
        Toast.makeText(
            this@AddProductActivity,
            resources.getString(R.string.product_uploaded_success_message),
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0]
                == PackageManager.PERMISSION_GRANTED
            ) {
                Constants.showImageChooser(this@AddProductActivity)
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
        if (resultCode == Activity.RESULT_OK
            && requestCode == Constants.PICK_IMAGE_REQUEST_CODE
            && data!!.data != null
        ) {

            binding.ivAddUpdateProduct.setImageDrawable(
                ContextCompat.getDrawable(
                    this@AddProductActivity,
                    R.drawable.ic_baseline_edit_24
                )
            )

            mSelectedImageFileUri = data.data!!

            try {
                GlideLoader(this@AddProductActivity)
                    .loadUserPicture(
                        mSelectedImageFileUri!!,
                        binding.ivProductImage
                    )
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}