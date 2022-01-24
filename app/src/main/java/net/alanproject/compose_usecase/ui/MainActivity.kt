package net.alanproject.compose_usecase.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import net.alanproject.compose_usecase.ui.meals.MealsCategoriesScreen
import net.alanproject.compose_usecase.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MealsCategoriesScreen()
            }
        }
    }
}
