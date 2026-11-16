package com.example.dogbreedsgallery.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "breed_images",
    primaryKeys = ["id", "breedId"],
    foreignKeys = [
        ForeignKey(
            entity = BreedEntity::class,
            parentColumns = ["id"], // идентификатор фотографии из TheDogAPI
            childColumns = ["breedId"], // идентификатор породы, к которой относится фотография
            onDelete = ForeignKey.CASCADE // адрес изображения
        )
    ],
    indices = [Index(value = ["breedId"])]
)
data class BreedImageEntity(
    val id: String,
    val breedId: Int,
    val url: String
)
