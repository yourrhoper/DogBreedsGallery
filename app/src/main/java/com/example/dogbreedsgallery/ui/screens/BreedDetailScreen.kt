
package com.example.dogbreedsgallery.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.dogbreedsgallery.data.local.BreedEntity

@Composable
fun BreedDetailScreen(
    breed: BreedEntity,
    onBack: () -> Unit,
    onFavoriteClick: (BreedEntity) -> Unit
) {
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
