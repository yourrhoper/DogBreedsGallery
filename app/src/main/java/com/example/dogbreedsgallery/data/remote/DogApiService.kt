package com.example.dogbreedsgallery.data.remote // описание запросов к API

import retrofit2.http.GET
import retrofit2.http.Query

interface DogApiService {

    @GET("breeds") // Получает список всех пород собак
    suspend fun getBreeds(): List<BreedDto>

    @GET("images/search") // Получает фотографии выбранной породы
    suspend fun getBreedImages(
        @Query("breed_ids") breedId: Int,
        @Query("limit") limit: Int = 20
    ): List<DogImageDto>
}
