package com.wcsm.confectionaryadmin.data.model.navigation

sealed class Screen(val route: String) {
    object NavigationHolder : Screen("navigation_holder")
    object Starter : Screen("starter")
    object Login : Screen("login")
    object UserRegister : Screen("user_register")
    object Main : Screen("main")
    object Orders : Screen("orders")
    object Customers : Screen("customers")
    object CustomerDetails : Screen("customer_details")
    object CreateOrder : Screen("create_order")
    object CreateCustomers : Screen("create_customers")
    object Info: Screen("info")
}