package com.example.setter.ui.screen.util

sealed class Screen(
    val route: String
) {
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
    object MainScreen: Screen("main_screen")

}
