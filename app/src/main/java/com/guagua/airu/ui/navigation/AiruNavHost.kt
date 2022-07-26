package com.guagua.airu.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.guagua.airu.ui.home.HomeScreen

@Composable
fun AiruNavHost(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Home.path
    ) {
        composable(Screen.Home.path) {
            HomeScreen(hiltViewModel())
        }
    }
}