package com.wcsm.confectionaryadmin.ui.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wcsm.confectionaryadmin.data.model.Screen
import com.wcsm.confectionaryadmin.ui.components.CustomBottomAppBar
import com.wcsm.confectionaryadmin.ui.components.CustomFloatActionButton
import com.wcsm.confectionaryadmin.ui.viewmodel.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationHolder(
    mainViewModel: MainViewModel = viewModel()
) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var icon: ImageVector? = null
    var onClick: (() -> Unit)? = {}

    when(currentDestination?.route) {
        Screen.Main.route -> {
            icon = Icons.Default.AddCircle
            onClick = {
                mainViewModel.changeShowChooseWhatWillCreateDialog(status = true)
                Log.i("#-#TESTE#-#", "CLICOU no FAB - Main Screen")
            }
        }
        Screen.Orders.route -> {
            icon = Icons.Default.PlaylistAdd
            onClick = {
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

    Scaffold(
        bottomBar = { CustomBottomAppBar(navController = navController) },
        floatingActionButton = {
            if (icon != null && onClick != null) {
                CustomFloatActionButton(icon = icon) {
                    onClick()
                }
            }
        }
    ) { paddingValues ->
        BottomNavGraph(
            navController = navController,
            mainViewModel = mainViewModel,
            paddingValues = paddingValues
        )
    }
}