package com.example.foodapp.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.foodapp.model.response.MealResponse
import com.example.foodapp.ui.theme.FoodAppTheme
import com.example.foodapp.ui.viewmodel.MealCategoryViewModel

@Composable
fun MealsCategoryScreen(navigationCallback: (String) -> Unit) {
    val viewModel: MealCategoryViewModel = viewModel()
    val meals = viewModel.mealState.value
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(items = meals, itemContent = { meal ->
            MealCategory(meal = meal, navigationCallback = navigationCallback)
        })
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MealCategory(meal: MealResponse, navigationCallback: (String) -> Unit) {

    var isExpanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clickable {
                navigationCallback(meal.id)
            }
    ) {
        Row(modifier = Modifier.animateContentSize()) {
            GlideImage(
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.CenterVertically)
                    .size(88.dp),
                model = meal.imageUrl,
                contentDescription = "Product image",
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterVertically)
                    .padding(16.dp)
            ) {
                Text(
                    text = meal.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = meal.description,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleSmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = if (isExpanded) 10 else 4
                )
            }
            Icon(
                imageVector = if (isExpanded)
                    Icons.Filled.KeyboardArrowUp
                else
                    Icons.Filled.KeyboardArrowDown,
                contentDescription = "Expand row icon",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { isExpanded = !isExpanded }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FoodAppTheme {
        MealsCategoryScreen({})
    }
}