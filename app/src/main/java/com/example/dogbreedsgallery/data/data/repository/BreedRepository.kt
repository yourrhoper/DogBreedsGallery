package com.example.dogbreedsgallery.data.repository

import com.example.dogbreedsgallery.data.local.BreedDao
import com.example.dogbreedsgallery.data.local.BreedEntity
import com.example.dogbreedsgallery.data.remote.DogApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class BreedRepository(
    private val api: DogApiService,
    private val dao: BreedDao
) {

    // Получение всех пород из локальной базы
    fun getAllBreeds(): Flow<List<BreedEntity>> {
        return dao.getAllBreeds()
    }

    // Получение избранных пород
    fun getFavoriteBreeds(): Flow<List<BreedEntity>> {
        return dao.getFavoriteBreeds()
    }

    // Получение одной породы по ID
    fun getBreedById(breedId: Int): Flow<BreedEntity?> {
        return dao.getBreedById(breedId)
    }

    // Поиск пород по названию
    fun searchBreeds(query: String): Flow<List<BreedEntity>> {
        return dao.searchBreeds(query)
    }

    // Загрузка пород из API и сохранение в Room
    suspend fun refreshBreeds() {
        val remoteBreeds = api.getBreeds()
        val localBreeds = dao.getAllBreeds().first()

        val localFavorites = localBreeds.associate {
            it.id to it.isFavorite
        }

        val breeds = remoteBreeds.map { breed ->
            BreedEntity(
                id = breed.id,
                name = breed.name,
                temperament = breed.temperament,
                origin = breed.origin,
                lifeSpan = breed.lifeSpan,
                description = breed.description,
                imageUrl = breed.image?.url ?: "",
                isFavorite = localFavorites[breed.id] ?: false
            )
        }

        dao.insertBreeds(breeds)
    }

    // Изменение статуса избранного
    suspend fun updateFavorite(breedId: Int, isFavorite: Boolean) {
        dao.updateFavorite(breedId, isFavorite)
    }

    // Загрузка дополнительных фотографий породы
    suspend fun getBreedImages(breedId: Int): List<String> {
        return api.getBreedImages(breedId)
            .map { it.url }
    }
}
