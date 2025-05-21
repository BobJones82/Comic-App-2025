package com.pas.comicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pas.comicapp.navigation.Screen
import com.pas.comicapp.presentation.comicdetails.ComicDetailsScreen
import com.pas.comicapp.presentation.comiclist.ComicListScreen
import com.pas.comicapp.ui.theme.ComicAppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity for the ComicApp.
 *
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComicAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    ComicNavHost()
                }
            }
        }
    }
}

/**
 * Composable function for the navigation host.
 *
 */
@Composable
fun ComicNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.ComicList.route) {
        composable(Screen.ComicList.route) {
            ComicListScreen(navController = navController)
        }
        composable(
            route = Screen.ComicDetails.route + "/{comicId}",
            arguments = listOf(navArgument("comicId") { type = NavType.StringType })
        ) { backStackEntry ->
            val comicId = backStackEntry.arguments?.getString("comicId")
            if (comicId != null) {
                ComicDetailsScreen(comicId = comicId, navController = navController)
            } else {
                Text("Error: Comic ID not found.")
            }
        }
    }
}