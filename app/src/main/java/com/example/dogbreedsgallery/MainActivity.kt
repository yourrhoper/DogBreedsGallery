package com.example.dogbreedsgallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dogbreedsgallery.ui.navigation.AppNavigation
import com.example.dogbreedsgallery.ui.theme.DogBreedsGalleryTheme
import com.example.dogbreedsgallery.viewmodel.BreedViewModel
import com.example.dogbreedsgallery.viewmodel.BreedViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as DogBreedsApplication
        setContent {
            DogBreedsGalleryTheme {
                val breedViewModel: BreedViewModel = viewModel(
                    factory = BreedViewModelFactory(app.container.breedRepository)
                )
                AppNavigation(viewModel = breedViewModel)
            }
        }
    }
}