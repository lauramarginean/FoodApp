package com.example.foodapp.ui.screens

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.foodapp.R
import com.example.foodapp.model.response.MealResponse
import com.example.foodapp.ui.theme.FoodAppTheme
import com.example.foodapp.ui.viewmodel.MealCategoryViewModel

const val THREE = 3

@Composable
fun HomeScreen(
    navigationCallback: (String) -> Unit
) {

    val viewModel: MealCategoryViewModel = viewModel()
    val meals = viewModel.mealState.value

    FoodAppTheme {
        Scaffold { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                //  .background(color = WhiteSmoke),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    BannerSection(navigationCallback)
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                vertical = 8.dp
                            ),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically),
                                text = "New Arrivals",
                                color = Color.Black,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Show all",
                                style = MaterialTheme.typography.labelLarge
                            )

                        }
                        LazyRow(
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            items(items = meals, itemContent = { meal ->
                                NewArrivalsSection(meal = meal)
                            })
                        }

                    }
                }

            }
        }

    }
}

@Composable
fun BannerSection( navigationCallback: (String) -> Unit) {
    Card(
        modifier = Modifier
            .height(325.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.banner),
                contentDescription = "Banner image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithCache {
                        val gradient = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = size.height / THREE,
                            endY = size.height
                        )
                        onDrawWithContent {
                            drawContent()
                            drawRect(brush = gradient, blendMode = BlendMode.Multiply)
                        }
                    }
            )

            Column(
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp, top = 188.dp, bottom = 28.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Sale up to 70%",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.Black
                    )
                    Button( onClick= { navigationCallback("destination_meals_list") },) {
                        Text(text = "Shop now")
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewArrivalsSection(meal: MealResponse) {

    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(180.dp)
            .padding(4.dp)
            .heightIn(min = 300.dp),
        shape = RectangleShape,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            IconToggleButton(
                checked = isFavorite,
                onCheckedChange = { isFavorite = !isFavorite},
                modifier = Modifier.align(Alignment.End)
            ) {
                val transition = updateTransition(isFavorite, label = "FavouriteIndicator")

                val tint by transition.animateColor(label = "tint") { isChecked ->
                    if (isChecked) Color.Red else Color.Gray
                }

                Icon(
                    tint = tint,
                    imageVector = if (isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    },
                    contentDescription = null
                )
            }
            GlideImage(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(
                        width = 124.dp,
                        height = 124.dp
                    )
                    .padding(8.dp),
                model = meal.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = meal.name,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            ReviewSection(imageSize = 12.dp, ratingSize = 10.sp)
            Spacer(modifier = Modifier.weight(weight = 2f))
            Button(onClick = {}){
                Text(text = "Add to cart")
            }

        }
    }

}

@Composable
private fun ReviewSection(imageSize: Dp, ratingSize: TextUnit) {
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
    ) {

        Icon(
            modifier = Modifier
                .width(imageSize)
                .height(imageSize),
            tint = Color.Yellow,
            imageVector = Icons.Default.Star,
            contentDescription = null
        )

        Icon(
            modifier = Modifier
                .width(imageSize)
                .height(imageSize),
            tint = Color.Yellow,
            imageVector = Icons.Default.Star,
            contentDescription = null
        )

        Icon(
            modifier = Modifier
                .width(imageSize)
                .height(imageSize),
            tint = Color.Yellow,
            imageVector = Icons.Default.Star,
            contentDescription = null,
        )

        Icon(
            modifier = Modifier
                .width(imageSize)
                .height(imageSize),
            tint =Color.Yellow,
            imageVector = Icons.Default.Star,
            contentDescription = null
        )

        Icon(
            modifier = Modifier
                .width(imageSize)
                .height(imageSize),
            tint = Color.Yellow,
            imageVector = Icons.Default.Star,
            contentDescription = null
        )

        Text(
            text = "1",
            color = Color.Black,
            fontSize = ratingSize,
            fontWeight = FontWeight.W400,
            lineHeight = 16.sp,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 3.dp)
        )
    }
}


