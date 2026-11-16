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
    fun getAllBreeds(): Flow<List<BreedEntity>>

    @Query("SELECT * FROM breeds WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavoriteBreeds(): Flow<List<BreedEntity>>

    @Query("SELECT * FROM breeds WHERE id = :breedId")
    fun getBreedById(breedId: Int): Flow<BreedEntity?>

    @Query("SELECT * FROM breeds WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchBreeds(query: String): Flow<List<BreedEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBreeds(breeds: List<BreedEntity>)

    @Update
    suspend fun updateBreed(breed: BreedEntity)

    @Query("UPDATE breeds SET name = :name, temperament = :temperament, origin = :origin, lifeSpan = :lifeSpan, description = :description, imageUrl = :imageUrl WHERE id = :id")
    suspend fun updateBreedDetails(
        id: Int,
        name: String,
        temperament: String?,
        origin: String?,
        lifeSpan: String?,
        description: String?,
        imageUrl: String
    )

    @Query("UPDATE breeds SET isFavorite = :isFavorite WHERE id = :breedId")
    suspend fun updateFavorite(breedId: Int, isFavorite: Boolean)
}
