package com.example.dogbreedsgallery.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BreedEntity::class], // содержит список сущностей
    version = 1, // первая версия базы
    exportSchema = false
)
abstract class DogDatabase : RoomDatabase() {

    abstract fun breedDao(): BreedDao // предоставляет доступ к DAO
}
