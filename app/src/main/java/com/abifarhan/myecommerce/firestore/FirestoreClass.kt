package com.abifarhan.myecommerce.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.abifarhan.myecommerce.model.Cart
import com.abifarhan.myecommerce.model.Product
import com.abifarhan.myecommerce.model.User
import com.abifarhan.myecommerce.utils.Constants
import com.abifarhan.myecommerce.view.ui.auth.login.LoginActivity
import com.abifarhan.myecommerce.view.ui.auth.register.RegisterActivity
import com.abifarhan.myecommerce.view.ui.dashboard.ui.dashboard.DashboardFragment
import com.abifarhan.myecommerce.view.ui.dashboard.ui.product.ProductsFragment
import com.abifarhan.myecommerce.view.ui.dashboard.ui.product.addproduct.AddProductActivity
import com.abifarhan.myecommerce.view.ui.dashboard.ui.product.detailproduct.ProductDetailActivity
import com.abifarhan.myecommerce.view.ui.profile.UserProfileActivity
import com.abifarhan.myecommerce.view.ui.settings.SettingsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()


    fun registerUser(activity: RegisterActivity, userInfo: User) {
        mFireStore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while registering.",
                    e
                )
            }
    }

    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    fun getUserDetails(activity: Activity) {
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.i(activity.javaClass.simpleName, document.toString())

                val user = document.toObject(User::class.java)
                Log.d(this.toString(), "ini data toObject Anda $user")

                val sharedPrefences =
                    activity.getSharedPreferences(
                        Constants.MYSHOPPAL_PREFERENCES,
                        Context.MODE_PRIVATE
                    )

                val editor: SharedPreferences.Editor =
                    sharedPrefences.edit()
                editor.putString(
                    Constants.LOGGED_IN_USERNAME,
                    "${user!!.firstName} ${user.lastName}"
                )
                editor.apply()

                when (activity) {
                    is LoginActivity -> {
                        activity.userLoggedInSuccess(user)
                    }

                    is SettingsActivity ->{
                        activity.userDetailsSuccess(user)
                    }
                }
            }
            .addOnFailureListener { e ->
                when (activity) {
                    is LoginActivity -> {
                        activity.hideProgressDialog()
                    }

                    is SettingsActivity -> {
                        activity.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting user details.",
                    e
                )
            }
    }

    fun updateUserProfileData(
        activity: Activity,
        userHashMap: HashMap<String, Any>
    ) {
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .update(userHashMap)
            .addOnSuccessListener {

                when (activity) {
                    is UserProfileActivity -> {
                        activity.userProfileUpdateSuccess()
                    }
                }
            }
            .addOnFailureListener {
                when (activity) {
                    is UserProfileActivity -> {
                        activity.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while updating the user details.",
                    it
                )
            }
    }

    fun uploadImageToCloudStorage(
        activity: Activity, imageFileURI: Uri?,
        imageType: String
    ) {
        val sRef: StorageReference =
            FirebaseStorage.getInstance()
                .reference.child(
                    imageType + System.currentTimeMillis() + "."
                            + Constants.getFileExtension(
                        activity,
                        imageFileURI
                    )
                )

        sRef.putFile(imageFileURI!!)
            .addOnSuccessListener { taskSnapshot ->
                Log.e(
                    "Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )

                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        Log.e("Downloadable Image URL", uri.toString())

                        when (activity) {
                            is UserProfileActivity -> {
                                activity.imageUploadSuccess(uri.toString())
                            }

                            is AddProductActivity -> {
                                activity.imageUploadSuccess(
                                    uri.toString()
                                )
                            }
                        }
                    }
            }

            .addOnFailureListener{exception ->
                when (activity) {
                    is UserProfileActivity -> {
                        activity.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    exception.message,
                    exception
                )
            }
    }

    fun uploadProductDetails(activity: AddProductActivity, product: Product) {
        mFireStore.collection(Constants.PRODUCTS)
            .document()
            .set(product, SetOptions.merge())
            .addOnSuccessListener {
                activity.productUploadSuccess()
            }
            .addOnFailureListener {
                activity.hideProgressDialog()

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while uploading the product details.",
                    it
                )
            }
    }

    fun getProductList(fragment: Fragment) {
        mFireStore.collection(Constants.PRODUCTS)
            .whereEqualTo(Constants.USER_ID, getCurrentUserID())
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e("Products List", "ini produk Anda ${document.documents.toString()}")

                // Here we have created a new instance for Products ArrayList.
                val productsList: ArrayList<Product> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val product = i.toObject(Product::class.java)
                    product!!.product_id = i.id

                    productsList.add(product)
                }

                when (fragment) {
                    is ProductsFragment -> {
                        fragment.successProductsListFromFireStore(
                            productsList)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error based on the base class instance.
                when (fragment) {
                    is ProductsFragment -> {
                        fragment.hideProgressDialog()
                    }
                }
                Log.e("Get Product List", "Error while getting product list.", e)
            }
    }

    fun getDashboardItemsList(fragment: DashboardFragment) {
        mFireStore.collection(Constants.PRODUCTS)
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e(fragment.javaClass.simpleName, document.documents.toString())

                // Here we have created a new instance for Products ArrayList.
                val productsList: ArrayList<Product> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val product = i.toObject(Product::class.java)!!
                    product.product_id = i.id
                    productsList.add(product)
                }

                // Pass the success result to the base fragment.
                fragment.successDashboardItemsList(productsList)
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error which getting the dashboard items list.
                fragment.hideProgressDialog()
                Log.e(fragment.javaClass.simpleName, "Error while getting dashboard items list.", e)
            }
    }

    fun deleteProduct(fragment: ProductsFragment, productId: String) {
        mFireStore.collection(Constants.PRODUCTS)
            .document(productId)
            .delete()
            .addOnSuccessListener {
                fragment.productDeleteSuccess()
            }
            .addOnFailureListener {
                fragment.hideProgressDialog()

                Log.e(
                    fragment.requireActivity().javaClass.simpleName,
                    "Error while deleting the product.",
                    it
                )
            }
    }

    fun getProductDetails(productDetailActivity: ProductDetailActivity, mProductId: String) {
        mFireStore.collection(Constants.PRODUCTS)
            .document(mProductId)
            .get()
            .addOnSuccessListener { document ->
                val product = document.toObject(Product::class.java)
                productDetailActivity.productDetailsSuccess(product!!)
            }
    }

    fun addCartItems(
        activity: ProductDetailActivity,
        addToCart: Cart
    ) {
        mFireStore.collection(Constants.CART_ITEMS)
            .document()
            .set(addToCart, SetOptions.merge())
            .addOnSuccessListener {
                activity.addToCartSuccess()
            }
    }

    fun checkIfItemExistInCart(
        activity: ProductDetailActivity,
        productId: String
    ) {
        mFireStore.collection(Constants.CART_ITEMS)
            .whereEqualTo(Constants.USER_ID, getCurrentUserID())
            .whereEqualTo(Constants.PRODUCT_ID, productId)
            .get()
            .addOnSuccessListener { document ->

                if (document.documents.size > 0) {
                    activity.productExistInCart()
                } else{
                    activity.hideProgressDialog()
                }
            }
    }
}