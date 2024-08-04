package com.wcsm.confectionaryadmin.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wcsm.confectionaryadmin.data.model.Screen
import com.wcsm.confectionaryadmin.ui.components.CustomBottomAppBar
import com.wcsm.confectionaryadmin.ui.components.CustomFloatActionButton

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationHolder() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var icon: ImageVector? = null
    var onClick: () -> Unit? = {}

    when(currentDestination?.route) {
        Screen.Main.route -> {
            icon = Icons.Default.AddCircle
            onClick = {}
        }
        Screen.Orders.route -> {
            icon = Icons.Default.NoteAdd
            onClick = {}
        }
        Screen.Customers.route -> {
            icon = Icons.Default.PersonAdd
            onClick = {}
        }
    }

    Scaffold(
        bottomBar = { CustomBottomAppBar(navController = navController) },
        floatingActionButton = {
            if (icon != null) {
                CustomFloatActionButton(icon = icon) {
                    onClick()
                }
            }
        }
    ) {
        BottomNavGraph(navController = navController)
    }
}