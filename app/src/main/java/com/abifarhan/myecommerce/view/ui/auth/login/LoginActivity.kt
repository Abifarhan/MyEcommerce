package com.abifarhan.myecommerce.view.ui.auth.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityLoginBinding
import com.abifarhan.myecommerce.firestore.FirestoreClass
import com.abifarhan.myecommerce.model.User
import com.abifarhan.myecommerce.utils.Constants
import com.abifarhan.myecommerce.view.ui.auth.forgotpassword.ForgotPasswordActivity
import com.abifarhan.myecommerce.view.ui.auth.register.RegisterActivity
import com.abifarhan.myecommerce.view.ui.base.BaseActivity
import com.abifarhan.myecommerce.view.ui.dashboard.DashBoardActivity
import com.abifarhan.myecommerce.view.ui.main.MainActivity
import com.abifarhan.myecommerce.view.ui.profile.UserProfileActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseActivity(), View.OnClickListener {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding.tvForgotPassword.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)
        binding.tvForgotPassword.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.btn_login -> {
                    logInRegisteredUser()
                }

                R.id.tv_register -> startActivity(
                    Intent(
                        this@LoginActivity,
                        RegisterActivity::class.java
                    )
                )

                R.id.tv_forgot_password ->startActivity(
                    Intent(
                        this@LoginActivity,
                        ForgotPasswordActivity::class.java
                    )
                )
            }
        }
    }

    private fun validateLoginDetails(): Boolean {
        return when{
            TextUtils.isEmpty(binding.etEmail.text.toString().trim() {it <= ' '}) ->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email),true)
                false
            }
            TextUtils.isEmpty(binding.etPassword.text.toString().trim() {it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else ->{
                showErrorSnackBar("Your details are valid.", false)
                true
            }
        }
    }

    private fun logInRegisteredUser() {
        if (validateLoginDetails()) {

            showProgressDialog(resources.getString(R.string.please_wait))

            val email = binding.etEmail.text.toString().trim() { it <= ' '}
            val password = binding.etPassword.text.toString().trim() { it <= ' '}

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        FirestoreClass().getUserDetails(
                            this@LoginActivity)
                    }else{
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(),
                        true)
                    }
                }
        }
    }

    fun userLoggedInSuccess(user: User?) {
        hideProgressDialog()
        Log.i("First Name: ", user!!.firstName)
        Log.i("Last Name: ", user.lastName)
        Log.i("Email: ", user.email)

        if (user.profileCompleted == 0) {
            val intent = Intent(this@LoginActivity,
            UserProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
            startActivity(intent)
        } else{
            startActivity(Intent(this@LoginActivity, DashBoardActivity::class.java))
        }

        finish()
    }
}