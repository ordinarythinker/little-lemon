package com.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavController
import com.littlelemon.ui.theme.LittleLemonColor
import kotlinx.coroutines.launch

@Composable
fun Onboarding(navController: NavController, snackbarHostState: SnackbarHostState) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val sharedPreferences = LocalContext.current.getSharedPreferences(Const.SETTINGS, Context.MODE_PRIVATE)

    var firstName by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var lastName by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }
    
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LittleLemonColor.cloud)
                .verticalScroll(scrollState, enabled = true)
        ) {
            Image(
                painter = painterResource(id = R.drawable.littlelemonimgtxt_nobg),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(180.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(0.dp, 30.dp, 0.dp, 30.dp)
            )
            Text(
                text = "Let's get to know you",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LittleLemonColor.green)
                    .padding(0.dp, 40.dp, 0.dp, 40.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h1
            )
            Text(
                text = "Personal information",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 40.dp, 16.dp, 30.dp),
                style = MaterialTheme.typography.h2
            )
            OutlinedTextField(
                value = firstName,
                label = { Text(text = "First name") },
                onValueChange = { newText ->
                    firstName = newText
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text(text = "Your first name") }
            )
            OutlinedTextField(
                value = lastName,
                label = { Text(text = "Last name") },
                onValueChange = { newText ->
                    lastName = newText
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text(text = "Your last name") }
            )
            OutlinedTextField(
                value = email,
                label = { Text(text = "Email") },
                onValueChange = { newText ->
                    email = newText
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text(text = "Email") }
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 70.dp, 16.dp, 16.dp),
                onClick = {
                    if (firstName.text.isBlank() || lastName.text.isBlank() || email.text.isBlank()) {
                        println("fields are blank")

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Registration unsuccessful. Please enter all data.",
                                duration = SnackbarDuration.Short
                            )
                        }
                    } else {
                        println("fields are not blank")

                        sharedPreferences.edit(commit = true) {
                            putBoolean(Const.IS_LOGGED_IN, true)
                            putString(Const.EMAIL, email.text)
                            putString(Const.FIRST_NAME, firstName.text)
                            putString(Const.LAST_NAME, lastName.text)
                        }

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Registration successful!",
                                duration = SnackbarDuration.Short
                            )
                        }

                        navController.navigate(Home.route)
                    }
                }
            ) {
                Text(
                    text = "Register",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

/*
@Preview
@Composable
fun OnboardingPreview() {
    LittleLemonTheme {
        Onboarding()
    }
}*/
