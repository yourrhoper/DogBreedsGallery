package com.example.dogbreedsgallery.viewmodel

import com.example.dogbreedsgallery.data.local.BreedEntity

data class BreedUiState(
    val breeds: List<BreedEntity> = emptyList(),
    val favoriteBreeds: List<BreedEntity> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val searchQuery: String = "",
    val breedImages: List<String> = emptyList(), // список URL дополнительных фотографий
    val isImagesLoading: Boolean = false, // состояние загрузки фотографий
    val imagesError: String? = null // ошибка при загрузке фотографий
)
