package net.alanproject.compose_usecase

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                UsersApplication()
            }
        }
    }
}

@Composable
fun UsersApplication(userProfiles: List<UserProfile> = userProfileList) {
    //navigation controller for jetpack to remember actual control state
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "users_list") {

        //navGraphBuilder to control how to build the navigation Graph
        //and navGraph is a map of screens
        composable(route = "users_list") {
            UserListScreen(userProfiles, navController)
        }

        //pass parameter
        composable(
            route = "user_details/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.IntType
            })
        ) { navBackStackEntry ->
            UserProfileDetailsScreen(navBackStackEntry.arguments!!.getInt("userId"), navController)
        }
    }
}


@Composable
fun UserListScreen(userProfiles: List<UserProfile>, navController: NavHostController?) {
    // top-level container to add or pass layout like TopBar, BottomBar, FAB or a Drawer
    Scaffold(topBar = {
        AppBar(
            title = "Users List",
            icon = Icons.Default.Home
        ){
            //do nothing
        }
    }) {
        //background
        Surface(
            // color, parameter of Surface is set as Material Theme.color.surface by default
            // so that color is derived from veryLightGrey in Theme.kt
            // and need to be careful as colors in Theme.kt is called in many places
//            color = MaterialTheme.colors.black,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn {
                //'items' iterate actual items
                items(userProfiles) { userProfile ->
                    ProfileCard(userProfile = userProfile) {
                        navController?.navigate("user_details/${userProfile.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun UserProfileDetailsScreen(userId: Int, navController: NavHostController?) {

    val userProfile = userProfileList.first { userProfile -> userProfile.id == userId}
    Scaffold(topBar = {
        AppBar(
            title = "Users profile details",
            icon = Icons.Default.ArrowBack
        ){
            //pop most current composable
            navController?.navigateUp()
        }
    }) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // reuse and customize composable
                ProfilePicture(userProfile.pictureUrl, userProfile.status, 240.dp)
                ProfileContent(userProfile.name, userProfile.status, Alignment.CenterHorizontally)
            }
        }
    }
}

@Composable
fun AppBar(title: String, icon: ImageVector, iconClickAction: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "",
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable(onClick = { iconClickAction.invoke() })
            )
        },
        title = { Text(title) }
    )
}

@Composable
fun ProfileCard(userProfile: UserProfile, clickAction: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .clickable(onClick = { clickAction.invoke() }),
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ProfilePicture(userProfile.pictureUrl, userProfile.status, 72.dp)
            ProfileContent(userProfile.name, userProfile.status, Alignment.Start)
        }

    }
}

@Composable
fun ProfilePicture(pictureUrl: String, onlineStatus: Boolean, imageSize: Dp) {
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
            modifier = Modifier.size(imageSize),
            contentDescription = "Profile picture description",
        )
    }
}

@Composable
fun ProfileContent(userName: String, onlineStatus: Boolean, alignment: Alignment.Horizontal) {
    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = alignment

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
fun UserProfileDetailsPreview() {
    CustomTheme {
        UserProfileDetailsScreen(userId = 0, null)
    }
}

@Preview(showBackground = true)
@Composable
fun UserListPreview() {
    CustomTheme {
        UserListScreen(userProfileList, null)
    }
}