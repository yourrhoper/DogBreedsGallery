package com.example.dogbreedsgallery.data.repository

import com.example.dogbreedsgallery.data.local.BreedDao
import com.example.dogbreedsgallery.data.local.BreedEntity
import com.example.dogbreedsgallery.data.local.BreedImageDao
import com.example.dogbreedsgallery.data.local.BreedImageEntity
import com.example.dogbreedsgallery.data.remote.DogApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class BreedRepository(
    private val api: DogApiService,
    private val dao: BreedDao,
    private val imageDao: BreedImageDao
) {
    fun getAllBreeds(): Flow<List<BreedEntity>> = dao.getAllBreeds()

    fun getFavoriteBreeds(): Flow<List<BreedEntity>> = dao.getFavoriteBreeds()

    fun getBreedById(breedId: Int): Flow<BreedEntity?> = dao.getBreedById(breedId)

    fun searchBreeds(query: String): Flow<List<BreedEntity>> = dao.searchBreeds(query)

    suspend fun refreshBreeds() {
        val remoteBreeds = api.getBreeds()
        val localBreeds = dao.getAllBreeds().first()
        val localFavorites = localBreeds.associate { it.id to it.isFavorite }

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

        breeds.forEach { breed ->
            dao.updateBreedDetails(
                id = breed.id,
                name = breed.name,
                temperament = breed.temperament,
                origin = breed.origin,
                lifeSpan = breed.lifeSpan,
                description = breed.description,
                imageUrl = breed.imageUrl
            )
        }
    }

    suspend fun updateFavorite(breedId: Int, isFavorite: Boolean) {
        dao.updateFavorite(breedId, isFavorite)
    }

    suspend fun getBreedImages(breedId: Int): List<String> {
        val localImages = imageDao.getImagesByBreedId(breedId)
        if (localImages.isNotEmpty()) {
            return localImages.map { it.url }
        }

        val remoteImages = api.getBreedImages(breedId)
        val images = remoteImages.map { image ->
            BreedImageEntity(
                id = image.id,
                breedId = breedId,
                url = image.url
            )
        }

        if (images.isNotEmpty()) {
            imageDao.insertImages(images)
        }

        return images.map { it.url }
    }
}
