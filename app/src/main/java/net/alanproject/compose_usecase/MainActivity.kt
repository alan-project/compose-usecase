package net.alanproject.compose_usecase

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import net.alanproject.compose_usecase.ui.UserProfile
import net.alanproject.compose_usecase.ui.theme.CustomTheme
import net.alanproject.compose_usecase.ui.theme.lightGreen
import net.alanproject.compose_usecase.ui.userProfileList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(userProfiles: List<UserProfile> = userProfileList) {
    // top-level container to add or pass layout like TopBar, BottomBar, FAB or a Drawer
    Scaffold(topBar = { AppBar() }) {
        //background
        Surface(
            // color, parameter of Surface is set as Material Theme.color.surface by default
            // so that color is derived from veryLightGrey in Theme.kt
            // and need to be careful as colors in Theme.kt is called in many places
//            color = MaterialTheme.colors.black,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn {
                items(userProfiles){userProfile->
                    ProfileCard(userProfile = userProfile)
                }
            }
        }
    }
}

@Composable
fun AppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                Icons.Default.Home,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 12.dp),
            )
        },
        title = { Text("Messaging Application users") }
    )
}

@Composable
fun ProfileCard(userProfile: UserProfile) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top),
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ProfilePicture(userProfile.pictureUrl, userProfile.status)
            ProfileContent(userProfile.name, userProfile.status)
        }

    }
}

@Composable
fun ProfilePicture(pictureUrl: String, onlineStatus: Boolean) {
    //by wrapping Image with Card, we can use shape, border, elevation parameter
    Card(
        shape = RoundedCornerShape(50),
        border = BorderStroke(
            width = 2.dp,
            //Color.Green is default value by Jetpack.
            //so to utilize colors in Theme.kt,
            // we need to define color and extension func
            color = if (onlineStatus)
                MaterialTheme.colors.lightGreen
            else Color.Red
        ),
        modifier = Modifier.padding(16.dp),
        elevation = 4.dp
    ) {

        Image(
            //loaded asynchronously
            painter = rememberImagePainter(
                data = pictureUrl,
                builder = {
                    transformations(CircleCropTransformation())
                }
            ),
            modifier = Modifier.size(72.dp),
            contentDescription = "Profile picture description",
        )
    }
}

@Composable
fun ProfileContent(userName: String, onlineStatus: Boolean) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        //transparency
        CompositionLocalProvider(
            LocalContentAlpha provides (
                    //0f..1f
                    if (onlineStatus) 1f else ContentAlpha.medium)
        ) {
            Text(
                text = userName,
                style = MaterialTheme.typography.h5
            )
        }
        CompositionLocalProvider(
            LocalContentAlpha provides (ContentAlpha.medium)
        ) {
            Text(
                text = if (onlineStatus)
                    "Active now"
                else "Offline",
                style = MaterialTheme.typography.body2
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CustomTheme {
        MainScreen()
    }
}