package com.example.dogbreedsgallery.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breeds")
data class BreedEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val temperament: String? = null,
    val origin: String? = null,
    val lifeSpan: String? = null,
    val description: String? = null,
    val imageUrl: String,
    val isFavorite: Boolean = false // добавлена ли порода в избранное
)
