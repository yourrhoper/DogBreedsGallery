package com.example.dogbreedsgallery.di

import android.content.Context
import androidx.room.Room
import com.example.dogbreedsgallery.data.local.DogDatabase
import com.example.dogbreedsgallery.data.remote.RetrofitClient
import com.example.dogbreedsgallery.data.repository.BreedRepository

class AppContainer(context: Context) {

    private val database = Room.databaseBuilder( // создаёт локальную базу данных
        context.applicationContext,
        DogDatabase::class.java,
        "dog_database"
    ).build()

    private val api = RetrofitClient.api // предоставляет доступ к API

    val breedRepository = BreedRepository( // получает оба объекта и объединяет их
        api = api,
        dao = database.breedDao()
    )
}
