package com.example.dogbreedsgallery.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BreedImageDao {

    @Query("SELECT * FROM breed_images WHERE breedId = :breedId")
    suspend fun getImagesByBreedId(breedId: Int): List<BreedImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(images: List<BreedImageEntity>)

    @Query("DELETE FROM breed_images WHERE breedId = :breedId")
    suspend fun deleteImagesByBreedId(breedId: Int)
}
