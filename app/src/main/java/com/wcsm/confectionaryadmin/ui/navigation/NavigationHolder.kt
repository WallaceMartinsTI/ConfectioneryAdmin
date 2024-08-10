package com.wcsm.confectionaryadmin.ui.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wcsm.confectionaryadmin.data.model.Screen
import com.wcsm.confectionaryadmin.ui.components.CustomBottomAppBar
import com.wcsm.confectionaryadmin.ui.components.CustomFloatActionButton
import com.wcsm.confectionaryadmin.ui.viewmodel.MainViewModel

@Composable
fun NavigationHolder(
    mainViewModel: MainViewModel = viewModel()
) {
    val navHostController = rememberNavController()

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var icon: ImageVector? = null
    var onClick: (() -> Unit)? = {}

    when(currentDestination?.route) {
        Screen.Orders.route -> {
            icon = Icons.Default.PlaylistAdd
            onClick = {
                navHostController.navigate(Screen.CreateOrder.route)
                Log.i("#-#TESTE#-#", "CLICOU no FAB - Orders Screen")
            }
        }
        Screen.Customers.route -> {
            icon = Icons.Default.PersonAddAlt1
            onClick = {
                Log.i("#-#TESTE#-#", "CLICOU no FAB - Customers Screen")
            }
        }
    }

    if(
        currentDestination?.route == Screen.CreateOrder.route ||
        currentDestination?.route == Screen.CreateCustomers.route
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BottomNavGraph(
                navController = navHostController,
                mainViewModel = mainViewModel
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
                mainViewModel = mainViewModel,
                paddingValues = paddingValues
            )
        }
    }
}