package com.wcsm.confectionaryadmin.data.model

sealed class Screen(val route: String) {
    object Starter : Screen("starter")
    object Login : Screen("login")
    object UserRegister : Screen("user_register")
    object Main : Screen("main")
    object Orders : Screen("orders")
    object Customers : Screen("customers")
    object CreateOrder : Screen("create_order")
    object CreateCustomers : Screen("create_customers")
}