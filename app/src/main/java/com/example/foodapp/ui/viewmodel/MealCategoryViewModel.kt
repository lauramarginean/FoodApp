package com.example.foodapp.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.model.response.MealResponse
import com.example.foodapp.repository.MealsRepository
import kotlinx.coroutines.launch

class MealCategoryViewModel(
    private val repository: MealsRepository = MealsRepository.getInstance(),
) : ViewModel() {

    init {
        viewModelScope.launch {
            val meals = getMeals()
            mealState.value = meals
        }
    }
    val mealState: MutableState<List<MealResponse>> = mutableStateOf(emptyList<MealResponse>())

    private suspend fun getMeals(): List<MealResponse> {
        return repository.getMeals().categories
    }

}
