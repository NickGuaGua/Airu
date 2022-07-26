package com.guagua.airu.ui.navigation

sealed class Screen(val path: String) {
    object Home : Screen("home")
    object Search : Screen("search")
}