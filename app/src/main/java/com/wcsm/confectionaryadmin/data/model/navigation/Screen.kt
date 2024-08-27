package com.wcsm.confectionaryadmin.data.model.navigation

sealed class Screen(val route: String) {
    data object NavigationHolder : Screen("navigation_holder")
    data object Starter : Screen("starter")
    data object Login : Screen("login")
    data object UserRegister : Screen("user_register")
    data object Main : Screen("main")
    data object Orders : Screen("orders")
    data object Customers : Screen("customers")
    data object CustomerDetails : Screen("customer_details")
    data object CreateOrder : Screen("create_order")
    data object CreateCustomers : Screen("create_customers")
    data object Info: Screen("info")
}