package com.abifarhan.myecommerce.view.ui.dashboard.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abifarhan.myecommerce.databinding.FragmentOrdersBinding

class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.textNotifications.text = "This is Notification Fragment"
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}