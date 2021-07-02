package com.abifarhan.myecommerce.view.ui.dashboard.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.abifarhan.myecommerce.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.textNotifications.text = "This is Notification Fragment"
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}