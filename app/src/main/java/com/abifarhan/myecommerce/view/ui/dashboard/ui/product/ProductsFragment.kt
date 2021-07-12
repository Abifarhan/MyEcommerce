package com.abifarhan.myecommerce.view.ui.dashboard.ui.product

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.FragmentProductsBinding
import com.abifarhan.myecommerce.firestore.FirestoreClass
import com.abifarhan.myecommerce.model.Product
import com.abifarhan.myecommerce.view.ui.base.BaseFragment
import com.abifarhan.myecommerce.view.ui.dashboard.ui.product.addproduct.AddProductActivity
import kotlinx.android.synthetic.main.fragment_products.*
import java.util.ArrayList

class ProductsFragment : BaseFragment() {

    private lateinit var mRootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(R.layout.fragment_products, container, false)
        return mRootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_add_product) {
            startActivity(Intent(activity, AddProductActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        getProductListFromFireStore()
    }

    private fun getProductListFromFireStore() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        // Call the function of Firestore class.
        FirestoreClass().getProductList(
            this@ProductsFragment)
    }


    fun successProductsListFromFireStore(productsList: ArrayList<Product>) {

        hideProgressDialog()
        Log.d("ini cek produk","ini list produk Anda $productsList")
        if (productsList.size > 0) {
            rv_my_product_items.visibility = View.VISIBLE
            tv_no_products_found.visibility = View.GONE

            rv_my_product_items.layoutManager =
                LinearLayoutManager(activity)
            rv_my_product_items.setHasFixedSize(true)

            val adapterProducts =
                MyProductListAdapter(requireActivity(),
                    productsList,
                    this@ProductsFragment)
            rv_my_product_items.adapter = adapterProducts
        } else {
            rv_my_product_items.visibility = View.GONE
            tv_no_products_found.visibility = View.VISIBLE
        }
    }

    fun deleteProduct(productID: String) {

        // Here we will call the delete function of the FirestoreClass. But, for now lets display the Toast message and call this function from adapter class.

        Toast.makeText(
            requireActivity(),
            "You can now delete the product. $productID",
            Toast.LENGTH_SHORT
        ).show()
        showAlertDialogToDeleteProduct(productID)
    }

    fun productDeleteSuccess() {
        hideProgressDialog()

        Toast.makeText(
            requireActivity(),
            resources.getString(R.string.product_delete_success_message),
            Toast.LENGTH_SHORT
        ).show()

        getProductListFromFireStore()
    }

    private fun showAlertDialogToDeleteProduct(productId: String) {

        val builder = AlertDialog.Builder(requireActivity())

        builder.setTitle("Delete")
        builder.setMessage("Are you sure want to delete the product?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes"){ dialogInterface, _ ->
            showProgressDialog(resources.getString(R.string.please_wait))

            FirestoreClass().deleteProduct(
                this@ProductsFragment, productId
            )

            dialogInterface.dismiss()
        }

        builder.setNegativeButton("No"){dialogInterface,_ ->
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}