package com.example.dogbreedsgallery.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogbreedsgallery.data.local.BreedEntity
import com.example.dogbreedsgallery.data.repository.BreedRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException

@OptIn(ExperimentalCoroutinesApi::class)
class BreedViewModel(
    private val repository: BreedRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BreedUiState()) // Получение данных
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<String>()
    val events = _events.asSharedFlow()

    private val searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            searchQuery
                .flatMapLatest { query ->
                    if (query.isBlank()) repository.getAllBreeds()
                    else repository.searchBreeds(query)
                }
                .collect { breeds ->
                    _uiState.value = _uiState.value.copy(breeds = breeds)
                }
        }
        viewModelScope.launch {
            repository.getFavoriteBreeds().collect { favorites ->
                _uiState.value = _uiState.value.copy(favoriteBreeds = favorites)
            }
        }
        refreshBreeds()
    }

    fun onSearchQueryChange(query: String) { // Поиск
        searchQuery.value = query
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun refreshBreeds() { // Обновление каталога
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )
            try {
                repository.refreshBreeds()
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                val errorMessage = if (e is HttpException) {
                    e.response()?.errorBody()?.string() ?: "HTTP ${e.code()}"
                } else {
                    e.localizedMessage ?: "Не удалось загрузить породы"
                }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = errorMessage
                )
            }
        }
    }

    fun loadBreedImages(breedId: Int) { // Загрузка фотографий породы
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                breedImages = emptyList(),
                isImagesLoading = true,
                imagesError = null
            )
            try {
                val images = repository.getBreedImages(breedId)
                _uiState.value = _uiState.value.copy(
                    breedImages = images,
                    isImagesLoading = false
                )
            } catch (e: Exception) {
                val errorMessage = if (e is HttpException) {
                    e.response()?.errorBody()?.string() ?: "HTTP ${e.code()}"
                } else {
                    e.localizedMessage ?: "Не удалось загрузить фотографии"
                }
                _uiState.value = _uiState.value.copy(
                    isImagesLoading = false,
                    imagesError = errorMessage
                )
            }
        }
    }

    fun toggleFavorite(breed: BreedEntity) { // Избранное
        viewModelScope.launch {
            try {
                val newFavoriteStatus = !breed.isFavorite
                repository.updateFavorite(
                    breedId = breed.id,
                    isFavorite = newFavoriteStatus
                )
                _events.emit(
                    if (newFavoriteStatus) "Добавлено в избранное"
                    else "Удалено из избранного"
                )
            } catch (e: Exception) {
                _events.emit("Не удалось изменить избранное")
            }
        }
    }
}
