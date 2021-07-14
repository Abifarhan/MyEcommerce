package com.abifarhan.myecommerce.view.ui.address

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abifarhan.myecommerce.R
import com.abifarhan.myecommerce.databinding.ActivityAddressListBinding
import com.abifarhan.myecommerce.firestore.FirestoreClass
import com.abifarhan.myecommerce.model.Address
import com.abifarhan.myecommerce.view.ui.base.BaseActivity
import java.util.ArrayList

class AddressListActivity : BaseActivity() {
    private var _binding: ActivityAddressListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddressListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        binding.tvAddAddress.setOnClickListener{
            val intent = Intent(this,
            AddEditAddressActivity::class.java)
            startActivity(intent)
        }

        getAddressList()
    }

    private fun getAddressList() {
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getAddressesList(this)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarAddressListActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
        }

        binding.toolbarAddressListActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun successAddressListFromFirestore(addressList: ArrayList<Address>) {
        hideProgressDialog()


        if (addressList.size > 0) {
            binding.rvAddressList.visibility = View.VISIBLE
            binding.tvNoAddressFound.visibility = View.GONE

            binding.rvAddressList.layoutManager = LinearLayoutManager(this)
            binding.rvAddressList.setHasFixedSize(true)

            val addressAdapter = AddressListAdapter(this,
            addressList)
            binding.rvAddressList.adapter = addressAdapter

//            val editSwipeHandler = object: SwipeToEditCallback(this){
//                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                    val adapter =
//                        binding.rvAddressList.adapter as AddressListAdapter
//                    adapter.notifyEditItem(
//                        this@AddressListActivity,
//                        viewHolder.adapterPosition
//                    )
//                }
//            }

            val editSwipeHandler = object: SwipeToEditCallback(this){
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val adapter = binding.rvAddressList.adapter as AddressListAdapter
                    adapter.notifyEditItem(
                        this@AddressListActivity,
                        viewHolder.adapterPosition
                    )
                }
            }
            val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
            editItemTouchHelper.attachToRecyclerView(binding.rvAddressList)

            val deleteSwipeHandler = object : SwipeToDeleteCallback(this) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    showProgressDialog(resources.getString(R.string.please_wait))

                    FirestoreClass().deleteAddress(
                        this@AddressListActivity,
                        addressList[viewHolder.adapterPosition].id
                    )
                }

            }
            val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
            deleteItemTouchHelper.attachToRecyclerView(binding.rvAddressList)
        }else{
            binding.rvAddressList.visibility = View.GONE
            binding.tvNoAddressFound.visibility = View.VISIBLE
        }
    }

    fun deleteAddressSuccess() {
        hideProgressDialog()
        Toast.makeText(
            this@AddressListActivity,
            resources.getString(R.string.err_your_address_deleted_successfully),
            Toast.LENGTH_SHORT
        ).show()

        getAddressList()
    }
}