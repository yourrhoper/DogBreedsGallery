
package com.example.dogbreedsgallery.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.dogbreedsgallery.data.local.BreedEntity

@Composable
fun BreedCard(
    breed: BreedEntity,
    onBreedClick: (Int) -> Unit,
    onFavoriteClick: (BreedEntity) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        onClick = { onBreedClick(breed.id) }
    ) {
        Column {
            AsyncImage(
                model = breed.imageUrl.takeIf { it.isNotBlank() },
                contentDescription = breed.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = breed.name,
                    fontSize = 18.sp
                )

                Text(
                    text = breed.origin ?: "Происхождение неизвестно",
                    fontSize = 13.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = { onFavoriteClick(breed) }
                    ) {
                        Text(
                            text = if (breed.isFavorite) {
                                "♥ В избранном"
                            } else {
                                "♡ В избранное"
                            }
                        )
                    }
                }
            }
        }
    }
}
