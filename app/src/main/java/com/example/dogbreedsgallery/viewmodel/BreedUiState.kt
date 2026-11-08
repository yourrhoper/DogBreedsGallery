package com.example.dogbreedsgallery.viewmodel

import com.example.dogbreedsgallery.data.local.BreedEntity

data class BreedUiState(
    val breeds: List<BreedEntity> = emptyList(), // список пород, который будет отображаться на экране
    val isLoading: Boolean = true, // идёт ли загрузка
    val error: String? = null, // сообщение об ошибке, если запрос не удался
    val searchQuery: String = "" // текст поискового запроса
)
