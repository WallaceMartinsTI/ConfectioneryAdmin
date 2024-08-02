package com.wcsm.confectionaryadmin.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.wcsm.confectionaryadmin.data.model.Screen
import com.wcsm.confectionaryadmin.ui.theme.AppBackground
import com.wcsm.confectionaryadmin.ui.theme.ConfectionaryAdminTheme
import com.wcsm.confectionaryadmin.ui.theme.Primary
import com.wcsm.confectionaryadmin.ui.view.LoginScreen
import com.wcsm.confectionaryadmin.ui.view.StarterScreen
import com.wcsm.confectionaryadmin.ui.view.UserRegisterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            ConfectionaryAdminTheme {
                SetBarColor(color = Primary)

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.Starter.route
                ) {

                    composable(route = Screen.Starter.route) {
                        StarterScreen(navController = navController)
                    }

                    composable(
                        route = Screen.Login.route,
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(500)
                            )
                        }
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
}