package com.guagua.airu.ui

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalView
import com.guagua.airu.data.model.AQIStatus
import com.guagua.airu.ui.theme.AQIStatusColor

fun AQIStatus.getColor() = when (this) {
    AQIStatus.GOOD -> AQIStatusColor.GOOD
    AQIStatus.MODERATE -> AQIStatusColor.MODERATE
    AQIStatus.UNHEALTHY_FOR_SENSITIVE_GROUPS -> AQIStatusColor.UNHEALTHY_FOR_SENSITIVE_GROUPS
    AQIStatus.UNHEALTHY -> AQIStatusColor.VERY_UNHEALTHY
    AQIStatus.VERY_UNHEALTHY -> AQIStatusColor.UNHEALTHY
    AQIStatus.HAZARDOUS -> AQIStatusColor.HAZARDOUS
    AQIStatus.UNKNOWN -> AQIStatusColor.UNKNOWN
}

enum class KeyboardState {
    Opened, Closed
}

@Composable
fun rememberKeyboardAsState(): KeyboardState {
    var keyboardState by remember { mutableStateOf(KeyboardState.Closed) }
    val view = LocalView.current
    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardState = if (keypadHeight > screenHeight * 0.15) {
                KeyboardState.Opened
            } else {
                KeyboardState.Closed
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }

    return keyboardState
}