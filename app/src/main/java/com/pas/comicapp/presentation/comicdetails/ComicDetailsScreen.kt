package com.pas.comicapp.presentation.comicdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.pas.comicapp.domain.model.Comic

/**
 * Composable function for the comic details screen.
 *
 * @param comicId
 * @param navController
 * @param viewModel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicDetailsScreen(
    comicId: String,
    navController: NavController,
    viewModel: ComicDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Comic Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (uiState) {
                is ComicDetailsUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is ComicDetailsUiState.Success -> {
                    val comic = (uiState as ComicDetailsUiState.Success).comic
                    ComicDetailContent(comic = comic)
                }
                is ComicDetailsUiState.Error -> {
                    val errorMessage = (uiState as ComicDetailsUiState.Error).message
                    Text("Error: $errorMessage", color = MaterialTheme.colorScheme.error)
                    // Optionally, add a retry button
                    Button(onClick = {
                        viewModel.fetchComicDetails(comicId)
                    }) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}

/**
 * Composable function for displaying content of the comic details.
 *
 * @param comic
 */
@Composable
fun ComicDetailContent(comic: Comic) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = comic.imageUrl,
            contentDescription = "Comic Cover",
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(0.7f),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = comic.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = comic.description,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Page Count: ${comic.pageCount}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (comic.creators.isNotEmpty()) {
            Text(
                text = "Creators: ${comic.creators.joinToString()}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        else{
            Text(
                text = "No creators available.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}