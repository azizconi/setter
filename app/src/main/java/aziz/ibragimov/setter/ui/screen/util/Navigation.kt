package com.example.setter.ui.screen.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.setter.ui.screen.login.LoginScreen
import com.example.setter.ui.screen.main.viewModel.SetterViewModel
import com.example.setter.ui.screen.register.RegisterScreen


@Composable
fun Navigation(
    onCheckPermission: () -> Unit,
    navController: NavHostController,
    setterViewModel: SetterViewModel
) {

    NavHost(
        navController = navController,
        startDestination = /*if (musics == null) */Screen.MainScreen.route/* else Screen.MainScreen.route*/
    ) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                navController = navController
            ) {
                onCheckPermission()
            }
        }

        composable(
            Screen.RegisterScreen.route
        ) {
            RegisterScreen(
                navController = navController
            ) {
                onCheckPermission()
            }
        }

//        composable(Screen.MainScreen.route) {
//
//
//
//                MainScreen(
////                    navController = navController,
////                    setterViewModel = setterViewModel
//                )
//
//        }
    }

}