package com.wcsm.confectionaryadmin.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wcsm.confectionaryadmin.data.model.Screen
import com.wcsm.confectionaryadmin.ui.view.CustomersScreen
import com.wcsm.confectionaryadmin.ui.view.MainScreen
import com.wcsm.confectionaryadmin.ui.view.OrdersScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(route = Screen.Main.route) {
            MainScreen()
        }

        composable(route = Screen.Orders.route) {
            OrdersScreen()
        }

        composable(route = Screen.Customers.route) {
            CustomersScreen()
        }
    }
}