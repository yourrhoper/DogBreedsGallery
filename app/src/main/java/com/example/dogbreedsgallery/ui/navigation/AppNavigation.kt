package com.example.dogbreedsgallery.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.dogbreedsgallery.ui.screens.CatalogScreen
import com.example.dogbreedsgallery.ui.screens.FavoritesScreen
import com.example.dogbreedsgallery.ui.screens.BreedDetailScreen
import com.example.dogbreedsgallery.viewmodel.BreedViewModel

@Composable
fun AppNavigation(viewModel: BreedViewModel) {
    val backStack = remember {
        mutableStateListOf<NavKey>(CatalogRoute)
    }
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = {
            if (backStack.lastOrNull() !is BreedDetailsRoute) {
                NavigationBar {
                    NavigationBarItem(
                        selected = backStack.lastOrNull() == CatalogRoute,
                        onClick = {
                            backStack.clear()
                            backStack.add(CatalogRoute)
                        },
                        icon = { Text("🐕") },
                        label = { Text("Каталог") }
                    )
                    NavigationBarItem(
                        selected = backStack.lastOrNull() == FavoritesRoute,
                        onClick = {
                            backStack.clear()
                            backStack.add(FavoritesRoute)
                        },
                        icon = { Text("♥") },
                        label = { Text("Избранное") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
            backStack = backStack,
            entryProvider = entryProvider {
                entry<CatalogRoute> {
                    CatalogScreen(
                        viewModel = viewModel,
                        onBreedClick = { breedId ->
                            backStack.add(BreedDetailsRoute(breedId))
                        }
                    )
                }
                entry<FavoritesRoute> {
                    FavoritesScreen(
                        viewModel = viewModel,
                        onBreedClick = { breedId ->
                            backStack.add(BreedDetailsRoute(breedId))
                        }
                    )
                }
                entry<BreedDetailsRoute> { route ->
                    val breed = (state.breeds + state.favoriteBreeds)
                        .firstOrNull { it.id == route.breedId }

                    if (breed != null) {
                        BreedDetailScreen(
                            breed = breed,
                            uiState = state,
                            onLoadImages = {
                                viewModel.loadBreedImages(breed.id)
                            },
                            onBack = {
                                if (backStack.size > 1) {
                                    backStack.removeLastOrNull()
                                }
                            },
                            onFavoriteClick = viewModel::toggleFavorite
                        )
                    } else {
                        Text("Информация о породе не найдена")
                    }
                }
            }
        )
    }
}
