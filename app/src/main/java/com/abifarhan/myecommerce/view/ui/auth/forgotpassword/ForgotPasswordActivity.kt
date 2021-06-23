package com.abifarhan.myecommerce.view.ui.auth.forgotpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityForgotPasswordBinding
import com.abifarhan.myecommerce.view.ui.base.BaseActivity
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : BaseActivity() {

    var _binding : ActivityForgotPasswordBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        binding.btnSubmit.setOnClickListener {
            val email = binding.etEmail.text.toString().trim() {it <= ' '}

            if (email.isEmpty()) {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
            } else {

                showProgressDialog(resources.getString(R.string.please_wait))

                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{task ->
                        hideProgressDialog()

                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                resources.getString(R.string.email_sent_success),
                                Toast.LENGTH_LONG
                            ).show()

                            finish()
                        }else {
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }
            }
        }

   }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarForgotPasswordActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        }

        binding.toolbarForgotPasswordActivity.setNavigationOnClickListener { onBackPressed() }
    }


}