package com.littlelemon

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.littlelemon.ui.theme.LittleLemonColor
import com.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun Home(navController: NavController? = null) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LittleLemonColor.cloud)
            .verticalScroll(scrollState, enabled = true)
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
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    LittleLemonTheme {
        Home()
    }
}