package com.example.dogbreedsgallery.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {
    @Query("SELECT * FROM breeds ORDER BY name ASC")
    fun getAllBreeds(): Flow<List<BreedEntity>> // Получить все породы
    @Query("SELECT * FROM breeds WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavoriteBreeds(): Flow<List<BreedEntity>> // Получить избранное
    @Query("SELECT * FROM breeds WHERE id = :breedId")
    fun getBreedById(breedId: Int): Flow<BreedEntity?> // Найти породу по ID
    @Query("SELECT * FROM breeds WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchBreeds(query: String): Flow<List<BreedEntity>> // Искать породы по названию
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreeds(breeds: List<BreedEntity>) // Добавить или заменить список пород
    @Update
    suspend fun updateBreed(breed: BreedEntity) // Обновить данные породы
    @Query("UPDATE breeds SET isFavorite = :isFavorite WHERE id = :breedId")
    suspend fun updateFavorite(breedId: Int, isFavorite: Boolean) // Изменить статус избранного
}
