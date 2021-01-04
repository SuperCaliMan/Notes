package com.example.compose.ui

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.font
import androidx.compose.ui.text.font.fontFamily
import com.example.compose.R

val Euclid = fontFamily(
    font(R.font.euclid_circular_regular),
    font(R.font.euclid_circular_medium, FontWeight.Medium),
    font(R.font.euclid_circular_light, FontWeight.Light)
)