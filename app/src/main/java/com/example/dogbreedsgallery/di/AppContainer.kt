package com.example.dogbreedsgallery.di

import android.content.Context
import androidx.room.Room
import com.example.dogbreedsgallery.data.local.DogDatabase
import com.example.dogbreedsgallery.data.local.MIGRATION_1_2
import com.example.dogbreedsgallery.data.remote.RetrofitClient
import com.example.dogbreedsgallery.data.repository.BreedRepository

class AppContainer(context: Context) {

    private val database = Room.databaseBuilder(
        context.applicationContext,
        DogDatabase::class.java,
        "dog_database"
    )
        .addMigrations(MIGRATION_1_2)
        .build()

    private val api = RetrofitClient.api

    val breedRepository = BreedRepository(
        api = api,
        dao = database.breedDao(),
        imageDao = database.breedImageDao()
    )
}
