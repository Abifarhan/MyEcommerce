package com.abifarhan.myecommerce.view.ui.base

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.DialogProgressBinding

open class BaseFragment : Fragment(){

    private lateinit var mProgressDialog: Dialog

    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(requireActivity())
        val customProgressBinding: DialogProgressBinding =
            DialogProgressBinding.inflate(layoutInflater)
        mProgressDialog.setContentView(customProgressBinding.root)
        customProgressBinding.tvProgressText.text = text
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)

        //Start the dialog and display it on screen.
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }
}