package com.example.dogbreedsgallery.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        BreedEntity::class,
        BreedImageEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class DogDatabase : RoomDatabase() {

    abstract fun breedDao(): BreedDao
    abstract fun breedImageDao(): BreedImageDao
}
