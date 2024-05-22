package com.example.foodapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.foodapp.model.response.MealResponse

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MealsDetailsScreen(meal: MealResponse?) {
    Column {
        Row {
            Card {
                GlideImage(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(88.dp),
                    model = meal?.imageUrl,
                    contentDescription = "Product image",
                )
            }
            Text(text = meal?.name?:"default name")
            Text(text = meal?.description?:"default name")
        }
        Button(onClick = { /*TODO*/ }) {

        }
    }
}
