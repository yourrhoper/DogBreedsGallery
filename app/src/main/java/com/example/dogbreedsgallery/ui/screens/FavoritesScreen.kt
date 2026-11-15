
package com.example.dogbreedsgallery.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dogbreedsgallery.ui.components.BreedCard
import com.example.dogbreedsgallery.viewmodel.BreedViewModel

@Composable
fun FavoritesScreen(
    viewModel: BreedViewModel,
    onBreedClick: (Int) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Избранное",
            fontSize = 26.sp
        )

        if (state.favoriteBreeds.isEmpty()) {
            Text("Пока нет избранных пород")
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = state.favoriteBreeds,
                    key = { it.id }
                ) { breed ->
                    BreedCard(
                        breed = breed,
                        onBreedClick = onBreedClick,
                        onFavoriteClick = viewModel::toggleFavorite
                    )
                }
            }
        }
    }
}
