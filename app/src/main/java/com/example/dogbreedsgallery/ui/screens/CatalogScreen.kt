
package com.example.dogbreedsgallery.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dogbreedsgallery.ui.components.BreedCard
import com.example.dogbreedsgallery.viewmodel.BreedViewModel

@Composable
fun CatalogScreen(
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
            text = "Dog Breeds Gallery",
            fontSize = 26.sp
        )

        TextField(
            value = state.searchQuery,
            onValueChange = viewModel::onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Поиск породы") },
            singleLine = true
        )

        if (state.error != null && state.breeds.isNotEmpty()) {
            Text(text = "Не удалось обновить каталог: ${state.error}")
            Button(onClick = viewModel::refreshBreeds) {
                Text("Повторить")
            }
        }

        if (state.isLoading && state.breeds.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text("Загружаем породы...")
            }
        } else if (state.error != null && state.breeds.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Не удалось загрузить породы")
                Text(state.error!!)
                Button(onClick = viewModel::refreshBreeds) {
                    Text("Повторить")
                }
            }
        } else if (state.breeds.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Породы не найдены")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = state.breeds,
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
