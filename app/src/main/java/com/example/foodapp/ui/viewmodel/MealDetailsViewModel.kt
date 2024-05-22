package com.example.foodapp.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.foodapp.model.response.MealResponse
import com.example.foodapp.repository.MealsRepository

class MealDetailsViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val repository: MealsRepository = MealsRepository.getInstance()
    var mealState = mutableStateOf<MealResponse?>(null)

    init {
        val mealId = savedStateHandle.get<String>("meal_category_id") ?: ""

        mealState.value = repository.getMeal(mealId)
    }

}