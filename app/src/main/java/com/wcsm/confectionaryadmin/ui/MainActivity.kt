package com.wcsm.confectionaryadmin.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.wcsm.confectionaryadmin.data.model.navigation.Screen
import com.wcsm.confectionaryadmin.ui.navigation.NavigationHolder
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.view.LoginScreen
import com.wcsm.confectionaryadmin.ui.view.StarterScreen
import com.wcsm.confectionaryadmin.ui.view.UserRegisterScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        installSplashScreen()

        setContent {
            ConfectionaryAdminTheme {
                SetBarColor(color = Primary)

                val navController = rememberNavController()

                val startDestination = if(isFirstTime(this)) {
                    Screen.Starter.route
                } else {
                    Screen.Login.route
                }

                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    enterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(500)
                        )
                    },
                    popEnterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(500)
                        )
                    }
                ) {
                    composable(route = Screen.Starter.route) {
                        StarterScreen(navController = navController)
                    }

                    composable(
                        route = Screen.Login.route,
                    ) {
                        LoginScreen(navController = navController)
                    }

                    composable(
                        route = Screen.UserRegister.route,
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(500)
                            )
                        }
                    ) {
                        UserRegisterScreen(navController = navController)
                    }

                    composable(route = Screen.NavigationHolder.route) {
                        NavigationHolder(externalNavController = navController)
                    }
                }
            }
        }
    }

    @Composable
    private fun SetBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setSystemBarsColor(color = color)
        }
    }

    private fun isFirstTime(context: Context): Boolean {
        val preferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val isFirstTime = preferences.getBoolean("is_first_time", true)

        if (isFirstTime) {
            preferences.edit().putBoolean("is_first_time", false).apply()
        }

        return isFirstTime
    }
}