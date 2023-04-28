package com.littlelemon

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.littlelemon.ui.theme.LittleLemonColor
import com.littlelemon.ui.theme.LittleLemonTheme
import java.util.*

@Composable
fun Home(navController: NavController? = null, databaseMenuItems: List<MenuItemRoom>? = null) {
    var searchText by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var chosenCategory by remember {
        mutableStateOf("")
    }

    var categories by remember {
        mutableStateOf(listOf<String>())
    }

    val cats = mutableSetOf<String>()
    databaseMenuItems!!.forEach { item -> cats.add(item.category) }
    categories = cats.toList()

    var menuItems = if (chosenCategory.isNotEmpty()) {
        val chosen = chosenCategory.lowercase()
        databaseMenuItems!!.filter { it.category.lowercase() == chosen }
    } else databaseMenuItems!!

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LittleLemonColor.cloud)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 30.dp, 30.dp, 30.dp)
        ) {
            Spacer(modifier = Modifier.weight(2f))
            Image(
                painter = painterResource(id = R.drawable.littlelemonimgtxt_nobg),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(180.dp)
                    .align(Alignment.CenterVertically)
            )
            
            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.julia),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .align(Alignment.CenterVertically)
                    .clickable {
                        println("clicked")
                        navController?.navigate(Profile.route)
                    }
            )
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(LittleLemonColor.green)
                .padding(16.dp)
        ) {
            Text(
                text = "Little Lemon",
                style = MaterialTheme.typography.h1
            )
            Text(
                text = "Chicago",
                style = MaterialTheme.typography.h2
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "We are family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(0.5f)
                )
                Image(
                    painter = painterResource(R.drawable.restaurant),
                    contentDescription = "Dishes picture",
                    modifier = Modifier
                        .weight(0.4f)
                        .width(120.dp)
                        .height(180.dp)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = LittleLemonColor.cloud,
                    cursorColor = LittleLemonColor.yellow,
                    leadingIconColor = LittleLemonColor.green,
                ),
                placeholder = { Text(text = "Enter search phrase") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
                }
            )

            if (searchText.text.isNotEmpty()) {
                menuItems = menuItems.filter { it.title.contains(searchText.text, ignoreCase = true) }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 32.dp, 16.dp, 16.dp)
        ) {
            Text(
                text = "ORDER FOR DELIVERY!",
                style = MaterialTheme.typography.h3
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 16.dp, 0.dp, 28.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                items(categories, itemContent = {text ->
                    val isChosen = chosenCategory == text

                    Text(
                        text = text,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                            .background(if (isChosen) Color(0xFFB1B1B1) else Color.LightGray)
                            .padding(10.dp, 8.dp, 10.dp, 8.dp)
                            .clickable {
                                chosenCategory = if (isChosen) "" else text
                            }
                    )
                })
            }
        }

        LazyColumn(modifier = Modifier.height(400.dp)) {
            items(menuItems) {item ->
                MenuItem(dish = item)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(dish: MenuItemRoom) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            Text(
                text = dish.title,
                style = MaterialTheme.typography.h3
            )
            Text(
                text = dish.description,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp)
                    .fillMaxWidth(.75f)
            )
            Text(
                text = "$${dish.price}",
                style = MaterialTheme.typography.body2,
            )
        }

        GlideImage(
            model = dish.image,
            contentDescription = "${dish.title} image",
            modifier = Modifier.clip(RoundedCornerShape(10.dp))
        )
    }
    Divider(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        thickness = 1.dp,
        color = LittleLemonColor.yellow
    )
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    LittleLemonTheme {
        Home()
    }
}
