package com.guagua.airu

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.guagua.airu.ui.home.HomeState
import com.guagua.airu.ui.home.HomeViewModel
import com.guagua.airu.ui.navigation.AiruNavHost
import com.guagua.airu.ui.theme.AiruTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AiruTheme {
                AiruNavHost(modifier = Modifier.fillMaxSize(), navController = navController)
            }
        }
    }
}