package com.littlelemon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun Home(navController: NavController? = null) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Home")
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    LittleLemonTheme {
        Home()
    }
}