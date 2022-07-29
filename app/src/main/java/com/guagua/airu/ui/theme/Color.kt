package com.guagua.airu.ui.theme

import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val Grey100 = Color(0xFFF4F4F4)
val Grey300 = Color(0xFFE0E0E0)
val Grey500 = Color(0xFF9E9E9E)
val Grey700 = Color(0xFF383838)
val Orange400 = Color(0xFFFFA726)

val Green600 = Color(0xFF43A047)
val Yellow600 = Color(0xFFFCD835)
val Orange600 = Color(0xFFFB8C00)
val Red600 = Color(0xFFE53935)
val Purple600 = Color(0xFF8E24AA)
val Pink900 = Color(0xFF880E4F)

object TextColor {
    val Title = Black
    val TitleLight = Grey700
    val Subtitle = Grey500
}

object AQIStatusColor {
    val GOOD = Green600
    val MODERATE = Yellow600
    val UNHEALTHY_FOR_SENSITIVE_GROUPS = Orange600
    val UNHEALTHY = Red600
    val VERY_UNHEALTHY = Pink900
    val HAZARDOUS = Pink900
    val UNKNOWN = Grey500
}