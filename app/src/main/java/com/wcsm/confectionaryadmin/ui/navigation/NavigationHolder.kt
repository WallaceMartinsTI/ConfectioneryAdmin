package com.wcsm.confectionaryadmin.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.wcsm.confectionaryadmin.ui.components.CustomBottomAppBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigationHolder() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { CustomBottomAppBar(navController = navController) }
    ) {
        BottomNavGraph(navController = navController)
    }
}