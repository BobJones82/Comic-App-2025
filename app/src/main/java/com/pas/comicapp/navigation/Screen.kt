package com.pas.comicapp.navigation

/**
 * Sealed class representing different screens in the application.
 *
 * @property route
 */
sealed class Screen(val route: String) {
    object ComicList : Screen("comic_list")
    object ComicDetails : Screen("comic_details") {
        fun createRoute(comicId: String) = "comic_details/$comicId"
    }
}