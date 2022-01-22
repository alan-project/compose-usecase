package net.alanproject.compose_usecase

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContent(content = {
//            MainScreen()
//        })
        // content: @Composable () -> Unit
        // UI Component
        setContent {
            Timber.d("setContent")
            MainScreen()
        }
    }
}

// UI component that has to be displayed: Starts with capital
@Composable
fun MainScreen() {
    // MainScreen control all the states

    Timber.d("MainScreen")
    // SnapshotStateList: can recompose whenever state changes
    // remember: remember state after recomposition
    val greetingListState = remember { mutableStateListOf("alan", "elin", "joyel") }
    val newNameStateContent = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GreetingList(
            greetingListState,
            newNameStateContent.value, //textFieldValue, state
            { input -> newNameStateContent.value = input }, //textFieldUpdate, state
            { greetingListState.add(newNameStateContent.value) }, //buttonClick
        )
    }
}

@Composable
fun GreetingList(
    namesList: List<String>,
    textFieldValue: String,
    textFieldUpdate: (input: String) -> Unit,
    buttonClick: () -> Unit
) {
    Timber.d("GreetingList")

    //TextView
    for (name in namesList) {
        Greeting(name = name)
    }

    //EditText
    TextField(value = textFieldValue, onValueChange = textFieldUpdate)

    //Button
    //RowScope is the last function parameter(lambda)
    Button(onClick = buttonClick) {
        Text("Add new")
    }
}

@Composable
fun Greeting(name: String) {
    Timber.d("Greeting $name")
    Text(
        text = "Hello $name!",
        style = MaterialTheme.typography.h5
/*
        style = TextStyle(
            color = Color.Blue,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.dp
        )
 */
    )
}

//same as composable in setContent
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen()
}