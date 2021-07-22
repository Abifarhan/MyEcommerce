package com.abifarhan.myecommerce.view.ui.dashboard.ui.orders.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityMyOrderDetailsBinding
import com.abifarhan.myecommerce.model.Order
import com.abifarhan.myecommerce.utils.Constants
import com.abifarhan.myecommerce.view.ui.dashboard.ui.orders.cart.list.CartItemsListAdapter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MyOrderDetailsActivity : AppCompatActivity() {

    private var _binding: ActivityMyOrderDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMyOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        var myOderDetails = Order()
        if (intent.hasExtra(Constants.EXTRA_MY_ORDER_DETAILS)){
            myOderDetails = intent.getParcelableExtra<Order>(
                Constants.EXTRA_MY_ORDER_DETAILS
            )!!
        }

        setupUI(myOderDetails)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarMyOrderDetailsActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(
                R.drawable.ic_baseline_arrow_back_ios_24
            )
        }

        binding.toolbarMyOrderDetailsActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupUI(orderDetails: Order) {
        binding.tvOrderDetailsId.text = orderDetails.title

        val dateFormat = "dd MM yyyy HH:mm"
        val formater = SimpleDateFormat(
            dateFormat, Locale.getDefault()
        )
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis =
            orderDetails.order_datetime
        val orderDateTime = formater.format(calendar.time)
        binding.tvOrderDetailsDate.text = orderDateTime

        val diffInMilliSeconds: Long =
            System.currentTimeMillis() - orderDetails.order_datetime
        val diffInHours: Long =
            TimeUnit.MILLISECONDS.toHours(diffInMilliSeconds)
        Log.e("Difference in Hours","$diffInHours")

        when{
            diffInHours < 1 ->{
                binding.tvOrderStatus.text =
                resources.getString(R.string.order_status_pending)
                binding.tvOrderStatus.setTextColor(
                    ContextCompat.getColor(
                        this@MyOrderDetailsActivity,
                        R.color.colorAccent
                    )
                )
            }

            diffInHours < 2 -> {
                binding.tvOrderStatus.text = resources.getString(R.string.order_status_in_process)
                binding.tvOrderStatus.setTextColor(
                    ContextCompat.getColor(
                        this@MyOrderDetailsActivity,
                        R.color.colorOrderStatusInProcess
                    )
                )
            }

            else -> {
                binding.tvOrderStatus.text =
                    resources.getString(
                        R.string.order_status_delivered
                    )
                binding.tvOrderStatus.setTextColor(
                    ContextCompat.getColor(
                        this@MyOrderDetailsActivity,
                        R.color.colorOrderStatusDelivered
                    )
                )
            }
        }

        binding.rvMyOrderItemsList.layoutManager =
            LinearLayoutManager(
                this@MyOrderDetailsActivity
            )
        binding.rvMyOrderItemsList.setHasFixedSize(true)

        val cartListAdapter =
            CartItemsListAdapter(
                this@MyOrderDetailsActivity,
                orderDetails.items, false
            )
        binding.rvMyOrderItemsList.adapter = cartListAdapter

        binding.tvMyOrderDetailsAddressType.text =
            orderDetails.address.type

        binding.tvMyOrderDetailsFullName.text =
            "${orderDetails.address.address}"

        binding.tvMyOrderDetailsAdditionalNote.text =
            orderDetails.address.additionalNote

        if (orderDetails.address.otherDetails.isNotEmpty()) {
            binding.tvMyOrderDetailsOtherDetails.visibility =
                View.VISIBLE
            binding.tvMyOrderDetailsOtherDetails.text =
                orderDetails.address.otherDetails
        } else {
            binding.tvMyOrderDetailsOtherDetails.visibility =
                View.GONE
        }
       binding.tvMyOrderDetailsMobileNumber.text =
           orderDetails.address.mobileNumber

        binding.tvOrderDetailsSubTotal.text =
            orderDetails.sub_total_amount

        binding.tvOrderDetailsShippingCharge.text =
            orderDetails.shipping_charge

        binding.tvOrderDetailsTotalAmount.text =
            orderDetails.total_amount
    }
}