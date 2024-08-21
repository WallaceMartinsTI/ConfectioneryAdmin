package com.wcsm.confectionaryadmin.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wcsm.confectionaryadmin.data.model.navigation.Screen
import com.wcsm.confectionaryadmin.ui.components.CustomBottomAppBar
import com.wcsm.confectionaryadmin.ui.components.CustomFloatActionButton

@Composable
fun NavigationHolder() {
    val navHostController = rememberNavController()

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var icon: ImageVector? = null
    var onClick: (() -> Unit)? = {}

    when(currentDestination?.route) {
        Screen.Orders.route -> {
            icon = Icons.AutoMirrored.Filled.PlaylistAdd
            onClick = {
                navHostController.navigate(Screen.CreateOrder.route)
            }
        }
        Screen.Customers.route -> {
            icon = Icons.Default.PersonAddAlt1
            onClick = {
                navHostController.navigate(Screen.CreateCustomers.route)
            }
        }
    }

    val routesWithoutScaffold = listOf(
        Screen.CreateOrder.route,
        Screen.CreateCustomers.route,
        Screen.CustomerDetails.route
    )

    if(currentDestination?.route in routesWithoutScaffold) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BottomNavGraph(
                navController = navHostController,
            )
        }
    } else {
        Scaffold(
            bottomBar = { CustomBottomAppBar(navController = navHostController) },
            floatingActionButton = {
                if(currentDestination?.route != Screen.Main.route) {
                    if (icon != null && onClick != null) {
                        CustomFloatActionButton(icon = icon) {
                            onClick()
                        }
                    }
                }
            }
        ) { paddingValues ->
            BottomNavGraph(
                navController = navHostController,
                paddingValues = paddingValues
            )
        }
    }
}