package com.abifarhan.myecommerce.view.ui.dashboard.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.FragmentOrdersBinding
import com.abifarhan.myecommerce.firestore.FirestoreClass
import com.abifarhan.myecommerce.model.Order
import com.abifarhan.myecommerce.view.ui.base.BaseFragment
import java.util.ArrayList

class OrdersFragment : BaseFragment() {

    private var _binding: FragmentOrdersBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun populateOrdersListInUI(ordersList: ArrayList<Order>) {
        hideProgressDialog()

        if (ordersList.size > 0) {
            binding.rvMyOrderItems.visibility = View.VISIBLE
            binding.tvNoOrdersFound.visibility = View.GONE

            binding.rvMyOrderItems.layoutManager = LinearLayoutManager(activity)
            binding.rvMyOrderItems.setHasFixedSize(true)

            val myOrdersAdapter =
                MyOrdersListAdapter(requireActivity(), ordersList)
            binding.rvMyOrderItems.adapter = myOrdersAdapter
        }
        else{
            binding.rvMyOrderItems.visibility = View.GONE
            binding.tvNoOrdersFound.visibility = View.VISIBLE
        }
    }

    private fun getMyOrdersList() {
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getMyOrdersList(
            this@OrdersFragment
        )
    }

    override fun onResume() {
        super.onResume()
        getMyOrdersList()
    }
}