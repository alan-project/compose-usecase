package net.alanproject.compose_usecase.ui.meals

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.alanproject.compose_usecase.model.MealsRepository
import net.alanproject.compose_usecase.model.response.MealResponse
import timber.log.Timber

class MealsCategoriesViewModel(
    private val repository: MealsRepository = MealsRepository()
) : ViewModel() {


    val mealState: MutableState<List<MealResponse>> = mutableStateOf(emptyList())


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val meals = getMeals()
            mealState.value = meals
        }
    }



    private suspend fun getMeals(): List<MealResponse> {
        return repository.getMeals().categories
    }
}