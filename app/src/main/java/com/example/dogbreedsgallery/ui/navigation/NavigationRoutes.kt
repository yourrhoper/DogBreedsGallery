
package com.example.dogbreedsgallery.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object CatalogRoute : NavKey

@Serializable
data object FavoritesRoute : NavKey

@Serializable
data class BreedDetailsRoute(
    val breedId: Int
) : NavKey
