
package com.example.dogbreedsgallery.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogbreedsgallery.data.local.BreedEntity
import com.example.dogbreedsgallery.data.repository.BreedRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class BreedViewModel(
    private val repository: BreedRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BreedUiState()) // Получение данных
    val uiState = _uiState.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            searchQuery
                .flatMapLatest { query ->
                    if (query.isBlank()) {
                        repository.getAllBreeds()
                    } else {
                        repository.searchBreeds(query)
                    }
                }
                .collect { breeds ->
                    _uiState.value = _uiState.value.copy(
                        breeds = breeds
                    )
                }
        }

        refreshBreeds()
    }

    fun onSearchQueryChange(query: String) { // Поиск
        searchQuery.value = query
        _uiState.value = _uiState.value.copy(
            searchQuery = query
        )
    }

    fun refreshBreeds() { // Обновление каталога
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                repository.refreshBreeds()
                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Не удалось загрузить породы"
                )
            }
        }
    }

    fun toggleFavorite(breed: BreedEntity) { // Избранное
        viewModelScope.launch {
            repository.updateFavorite(
                breedId = breed.id,
                isFavorite = !breed.isFavorite
            )
        }
    }
}
