package com.example.dogbreedsgallery.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.dogbreedsgallery.data.local.BreedEntity
import com.example.dogbreedsgallery.viewmodel.BreedUiState

@Composable
fun BreedDetailScreen(
    breed: BreedEntity,
    uiState: BreedUiState,
    onLoadImages: () -> Unit,
    onBack: () -> Unit,
    onFavoriteClick: (BreedEntity) -> Unit
) {
    LaunchedEffect(breed.id) {
        onLoadImages()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(onClick = onBack) {
            Text("← Назад")
        }

        AsyncImage(
            model = breed.imageUrl.takeIf { it.isNotBlank() },
            contentDescription = breed.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = breed.name,
            fontSize = 28.sp
        )

        Text(
            text = "Фотографии породы",
            fontSize = 20.sp
        )

        when {
            uiState.isImagesLoading -> {
                CircularProgressIndicator()
            }
            uiState.imagesError != null -> {
                Text("Не удалось загрузить фотографии: ${uiState.imagesError}")
                Button(onClick = onLoadImages) {
                    Text("Повторить")
                }
            }
            uiState.breedImages.isEmpty() -> {
                Text("Дополнительные фотографии не найдены")
            }
            else -> {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.breedImages) { imageUrl ->
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "Фотография ${breed.name}",
                            modifier = Modifier
                                .size(width = 220.dp, height = 160.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        Text(
            text = "Происхождение",
            fontSize = 18.sp
        )
        Text(breed.origin ?: "Неизвестно")

        Text(
            text = "Темперамент",
            fontSize = 18.sp
        )
        Text(breed.temperament ?: "Нет информации")

        Text(
            text = "Продолжительность жизни",
            fontSize = 18.sp
        )
        Text(breed.lifeSpan ?: "Нет информации")

        Text(
            text = "Описание",
            fontSize = 18.sp
        )
        Text(breed.description ?: "Описание отсутствует")

        Button(
            onClick = { onFavoriteClick(breed) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (breed.isFavorite) "Удалить из избранного"
                else "Добавить в избранное"
            )
        }
    }
}
