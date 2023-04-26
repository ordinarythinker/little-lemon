package com.littlelemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.littlelemon.ui.theme.LittleLemonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            val scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)

            LittleLemonTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    Surface(
                        color = MaterialTheme.colors.background,
                        modifier = Modifier.padding(it)
                    ) {
                        Navigation(snackbarHostState)
                    }
                }
            }
        }
    }
}

@Composable
fun Navigation(snackbarHostState: SnackbarHostState) {
    val navController = rememberNavController()
    var isLoggedIn by remember { mutableStateOf(false) }
    val preferences = LocalContext.current.getSharedPreferences(Const.SETTINGS, Context.MODE_PRIVATE)

    isLoggedIn = preferences.getBoolean(Const.IS_LOGGED_IN, false)
    val startDestination = if (isLoggedIn) Home.route else OnBoarding.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Home.route) {
            Home(navController = navController)
        }
        composable(OnBoarding.route) {
            Onboarding(navController = navController, snackbarHostState = snackbarHostState)
        }
        composable(Profile.route) {
            Profile()
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LittleLemonTheme {
        Navigation()
    }
}*/
