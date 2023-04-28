package com.littlelemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            val scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)

            val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())

            LittleLemonTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    Surface(
                        color = MaterialTheme.colors.background,
                        modifier = Modifier.padding(it)
                    ) {
                        Navigation(snackbarHostState, databaseMenuItems)
                    }
                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            if (database.menuItemDao().isEmpty()) {
                val menuItemsNetwork = fetchMenu()
                saveMenuToDatabase(menuItemsNetwork)
            }
        }
    }

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        return httpClient
            .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body<MenuNetwork>()
            .menu
    }

    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
    }
}

@Composable
fun Navigation(snackbarHostState: SnackbarHostState, databaseMenuItems: List<MenuItemRoom>) {
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
            Home(navController = navController, databaseMenuItems = databaseMenuItems)
        }
        composable(OnBoarding.route) {
            Onboarding(navController = navController, snackbarHostState = snackbarHostState)
        }
        composable(Profile.route) {
            Profile()
        }
    }
}
