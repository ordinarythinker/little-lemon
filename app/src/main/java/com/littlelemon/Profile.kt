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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavController
import com.littlelemon.ui.theme.LittleLemonColor
import com.littlelemon.ui.theme.LittleLemonTheme
import kotlinx.coroutines.launch

@Composable
fun Profile(navController: NavController? = null, snackbarHostState: SnackbarHostState? = null) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val sharedPreferences = LocalContext.current.getSharedPreferences(Const.SETTINGS, Context.MODE_PRIVATE)

    var firstName by remember {
        mutableStateOf(TextFieldValue(
            sharedPreferences.getString(Const.FIRST_NAME, "")!!
        ))
    }

    var lastName by remember {
        mutableStateOf(TextFieldValue(
            sharedPreferences.getString(Const.LAST_NAME, "")!!
        ))
    }

    var email by remember {
        mutableStateOf(TextFieldValue(
            sharedPreferences.getString(Const.EMAIL, "")!!
        ))
    }

    val saveAndNotify: (String, String) -> Unit = { tag, newValue ->
        sharedPreferences.edit(commit = true) {
            putString(tag, newValue)
            scope.launch {
                snackbarHostState?.showSnackbar(
                    message = "Update is saved",
                    duration = SnackbarDuration.Short
                )
            }
        }
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
                text = "Profile information:",
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
                    saveAndNotify(Const.FIRST_NAME, newText.text)
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
                    saveAndNotify(Const.LAST_NAME, newText.text)
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
                    saveAndNotify(Const.EMAIL, newText.text)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text(text = "Email") }
            )

            Spacer(modifier = Modifier.weight(4f))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 70.dp, 16.dp, 16.dp),
                onClick = {
                    sharedPreferences.edit(commit = true) {
                        putBoolean(Const.IS_LOGGED_IN, false)
                    }

                    scope.launch {
                        snackbarHostState?.showSnackbar(
                            message = "Logged out",
                            duration = SnackbarDuration.Short
                        )
                    }
                    navController?.navigate(OnBoarding.route)
                },
            ) {
                Text(
                    text = "Log out",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.button
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    LittleLemonTheme {
        Profile()
    }
}