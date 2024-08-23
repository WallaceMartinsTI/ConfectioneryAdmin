package com.wcsm.confectionaryadmin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.wcsm.confectionaryadmin.data.model.entities.Customer
import com.wcsm.confectionaryadmin.data.model.entities.Order
import com.wcsm.confectionaryadmin.data.model.entities.FirestoreUser
import com.wcsm.confectionaryadmin.data.model.entities.User
import com.wcsm.confectionaryadmin.data.repository.CustomerRepository
import com.wcsm.confectionaryadmin.data.repository.OrderRepository
import com.wcsm.confectionaryadmin.data.repository.UserRepository
import com.wcsm.confectionaryadmin.ui.util.Constants.FIRESTORE_TAG
import com.wcsm.confectionaryadmin.ui.util.toUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository
) : ViewModel() {
    private val _ordersFromCloud = MutableStateFlow<List<Order>>(emptyList())
    private val _customersFromCloud = MutableStateFlow<List<Customer>>(emptyList())

    private val _isSyncLoading = MutableStateFlow(false)
    val isSyncLoading = _isSyncLoading.asStateFlow()

    private val _isSyncSuccess = MutableStateFlow(false)
    val isSyncSuccess = _isSyncSuccess.asStateFlow()

    private val _fetchedFirestoreUser = MutableStateFlow<User?>(null)
    val fetchedUser = _fetchedFirestoreUser.asStateFlow()

    private var _currentUser: FirebaseUser? = null

    init {
        Log.i("#-# TESTE #-#", "InfoViewModel INIT")
        viewModelScope.launch {
            _currentUser = userRepository.getCurrentUser()
        }
    }

    fun fetchUserData() {
        viewModelScope.launch {
            val currentUser = userRepository.getCurrentUser()
            if(currentUser != null) {
                try {
                    userRepository.getUserData(currentUser.uid)
                        .addOnSuccessListener {  document ->
                            Log.i(FIRESTORE_TAG, "User data fetched successfully!")
                            val firestoreUser = document.toObject(FirestoreUser::class.java)

                            if(firestoreUser != null) {
                                viewModelScope.launch {
                                    val customersQuantity = customerRepository.getUserCustomersQuantity(currentUser.uid)
                                    val ordersQuantity = orderRepository.getUserOrdersQuantity(currentUser.uid)
                                    _fetchedFirestoreUser.value = firestoreUser.copy(
                                        customers = customersQuantity,
                                        orders = ordersQuantity
                                    ).toUser()
                                }
                            }
                        }
                        .addOnFailureListener {
                            Log.e(FIRESTORE_TAG, "User data fetched successfully!", it)
                        }
                } catch (e: Exception) {
                    Log.e(FIRESTORE_TAG, "Error converting data fetched into user object", e)
                }
            }
        }
    }

    fun fetchUserCustomersAndOrders() {
        _isSyncSuccess.value = false
        _isSyncLoading.value = true
        viewModelScope.launch {
            try {
                fetchCustomersFromFirestore()

                fetchOrdersFromFirestore()

                Log.i("#-# SYNC #-#", "All user data fetched successfully!")
                Log.i("#-# SYNC #-#", "Now, trying to save in local Room database...")
                saveFirestoreDataToRoom()
                fetchUserData()
            } catch (e: Exception) {
                Log.e("#-# SYNC #-#", "Error fetching all user data.", e)
            }
        }
    }

    private suspend fun saveFirestoreDataToRoom() {
        Log.i("#-# TESTE #-#", "InfoViewModel - saveFirestoreDataToRoom")
        try {
            val customers = _customersFromCloud.value
            val orders = _ordersFromCloud.value
            Log.i("#-# TESTE #-#", "orders: $orders")
            customerRepository.saveCustomersToLocalDatabase(customers)
            orderRepository.saveOrdersToLocalDatabase(orders)
            Log.i("#-# SYNC #-#", "firestore data saved in room with success!")
            _isSyncSuccess.value = true
        } catch (e: Exception) {
            Log.e("#-# SYNC #-#", "Error saving firestore data in room.", e)
        }
        _isSyncLoading.value = false
        Log.i("#-# TESTE #-#", "=========================================")
    }

    private suspend fun fetchOrdersFromFirestore() {
        Log.i("#-# TESTE #-#", "InfoViewModel - fetchOrdersFromFirestore")
        if(_currentUser != null) {
            try {
                val orders = orderRepository.getOrdersFromFirestore(_currentUser!!.uid)
                Log.i("#-# TESTE #-#", "orders: $orders")
                _ordersFromCloud.value = orders
                Log.i("#-# SYNC #-#", "fetchOrdersFromFirestore success!")
            } catch (e: Exception) {
                Log.e("#-# SYNC #-#", "Error in fetchOrdersFromFirestore function.", e)
            }
        }
        Log.i("#-# TESTE #-#", "=========================================")
    }

    private suspend fun fetchCustomersFromFirestore() {
        try {
            val customers = customerRepository.getCustomersFromFirestore(_currentUser!!.uid)
            _customersFromCloud.value = customers
            Log.i("#-# SYNC #-#", "fetchCustomersFromFirestore success!")
        } catch (e: Exception) {
            Log.e("#-# SYNC #-#", "Erro in fetchCustomersFromFirestore function.", e)
        }
    }
}