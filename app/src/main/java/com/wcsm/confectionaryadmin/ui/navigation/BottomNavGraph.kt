package com.wcsm.confectionaryadmin.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wcsm.confectionaryadmin.data.model.Screen
import com.wcsm.confectionaryadmin.ui.view.CreateCustomerScreen
import com.wcsm.confectionaryadmin.ui.view.CreateOrderScreen
import com.wcsm.confectionaryadmin.ui.view.CustomerDetailsScreen
import com.wcsm.confectionaryadmin.ui.view.CustomersScreen
import com.wcsm.confectionaryadmin.ui.view.MainScreen
import com.wcsm.confectionaryadmin.ui.view.OrdersScreen
import com.wcsm.confectionaryadmin.ui.viewmodel.CustomersViewModel
import com.wcsm.confectionaryadmin.ui.viewmodel.MainViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    paddingValues: PaddingValues = PaddingValues(),
    customersViewModel: CustomersViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(route = Screen.Main.route) {
            MainScreen(
                paddingValues = paddingValues,
                mainViewModel = mainViewModel
            )
        }

        composable(route = Screen.Orders.route) {
            OrdersScreen(paddingValues = paddingValues)
        }

        composable(route = Screen.Customers.route) {
            CustomersScreen(
                navController = navController,
                paddingValues = paddingValues,
                customersViewModel = customersViewModel
            )
        }

        composable(route = Screen.CreateOrder.route) {
            CreateOrderScreen(navController = navController)
        }

        composable(route = Screen.CreateCustomers.route) {
            CreateCustomerScreen(navController = navController)
        }

        composable(route = Screen.CustomerDetails.route){
            CustomerDetailsScreen(
                navController = navController,
                customersViewModel = customersViewModel
            )
        }
    }
}