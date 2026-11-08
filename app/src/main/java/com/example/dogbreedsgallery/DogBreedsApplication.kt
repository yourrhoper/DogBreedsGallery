package com.example.dogbreedsgallery

import android.app.Application
import com.example.dogbreedsgallery.di.AppContainer

class DogBreedsApplication : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
