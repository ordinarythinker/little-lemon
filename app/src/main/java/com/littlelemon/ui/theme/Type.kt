package com.littlelemon.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontSize = 30.sp,
        color = LittleLemonColor.yellow,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif
    ),
    h2 = TextStyle(
        color = LittleLemonColor.cloud,
        fontSize = 22.sp,
        fontFamily = FontFamily.Serif
    ),
    h3 = TextStyle(
        color = Color.Black,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
    ),
    h5 = TextStyle(
        fontSize = 18.sp,
        color = LittleLemonColor.cloud
    ),
    body1 = TextStyle(
        color = LittleLemonColor.green
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = LittleLemonColor.charcoal
    ),
    button = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = LittleLemonColor.charcoal
    )
)