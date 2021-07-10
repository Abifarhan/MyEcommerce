package com.abifarhan.myecommerce.view.ui.dashboard.ui.product

import android.content.Intent
import android.os.Bundle
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
import java.util.ArrayList

class ProductsFragment : BaseFragment() {

    private var _binding: FragmentProductsBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onResume() {
        super.onResume()
        getProductListFromFirestore()
    }

    private fun getProductListFromFirestore() {
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getProductList(this@ProductsFragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_add_product) {
            startActivity(Intent(activity, AddProductActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun successProductsListFromFirestore(productList: ArrayList<Product>) {
        hideProgressDialog()

        if (productList.size > 0) {
            binding.rvMyProductItems.visibility = View.VISIBLE
            binding.tvNoProductsFound.visibility = View.GONE

            binding.rvMyProductItems.layoutManager = LinearLayoutManager(activity)
            binding.rvMyProductItems.setHasFixedSize(true)

            val adapterProducts =
                MyProductListAdapter(requireActivity(),
                productList,
                this@ProductsFragment)

            binding.rvMyProductItems.adapter = adapterProducts
        }
    }

    fun deleteProduct(productId: String) {
        Toast.makeText(
            requireActivity(),
            "You can now delete the product. $productId",
            Toast.LENGTH_SHORT
        ).show()
    }
}