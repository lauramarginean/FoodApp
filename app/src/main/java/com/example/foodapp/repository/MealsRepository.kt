package com.example.foodapp.repository

import com.example.foodapp.model.api.MealsWebService
import com.example.foodapp.model.response.MealResponse
import com.example.foodapp.model.response.MealsCategoryResponse

class MealsRepository(
    private val webService: MealsWebService = MealsWebService()
){

    private var cachedMeals = listOf<MealResponse>()
    suspend fun getMeals(): MealsCategoryResponse {
        val response = webService.getMeals()
        cachedMeals = response.categories
        return response
    }

    fun getMeal(id: String): MealResponse? {
        return cachedMeals.firstOrNull {
            it.id == id
        }
    }

    companion object {
        private var instance: MealsRepository? = null

        fun getInstance() = instance?: synchronized(this) {
            instance ?: MealsRepository().also { instance = it }
        }
    }
}
