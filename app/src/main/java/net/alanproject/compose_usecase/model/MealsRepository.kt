package net.alanproject.compose_usecase.model

import net.alanproject.compose_usecase.model.api.MealsWebService
import net.alanproject.compose_usecase.model.response.MealsCategoriesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealsRepository(private val webService:MealsWebService = MealsWebService()) {

    suspend fun getMeals(): MealsCategoriesResponse{
        return webService.getMeals()
    }
}