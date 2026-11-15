package com.example.dogbreedsgallery.viewmodel

import com.example.dogbreedsgallery.data.local.BreedEntity

data class BreedUiState(
    val breeds: List<BreedEntity> = emptyList(),
    val favoriteBreeds: List<BreedEntity> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val searchQuery: String = ""
)