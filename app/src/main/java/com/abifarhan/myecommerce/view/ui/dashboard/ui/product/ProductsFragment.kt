package com.abifarhan.myecommerce.view.ui.dashboard.ui.product

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.FragmentProductsBinding
import com.abifarhan.myecommerce.view.ui.dashboard.ui.product.addproduct.AddProductActivity

class ProductsFragment : Fragment() {

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
        val root: View = binding.root

        binding.textHome.text = "This is home Fragment"
        return root
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}