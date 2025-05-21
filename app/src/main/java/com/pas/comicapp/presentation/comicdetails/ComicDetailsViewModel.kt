package com.pas.comicapp.presentation.comicdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pas.comicapp.data.repository.ComicRepository
import com.pas.comicapp.domain.model.Comic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

/**
 * ViewModel class for handling comic details related operations.
 *
 * @property comicRepository
 * @constructor
 * TODO
 *
 * @param savedStateHandle
 */
@HiltViewModel
class ComicDetailsViewModel @Inject constructor(
    private val comicRepository: ComicRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<ComicDetailsUiState>(ComicDetailsUiState.Loading)
    val uiState: StateFlow<ComicDetailsUiState> = _uiState

    init {
        val comicId = savedStateHandle.get<String>("comicId")
        if (comicId != null) {
            fetchComicDetails(comicId)
        } else {
            _uiState.value = ComicDetailsUiState.Error("Comic ID is missing.")
        }
    }

    /**
     * Fetches the details of a specific comic.
     *
     * @param comicId
     */
    fun fetchComicDetails(comicId: String) {
        viewModelScope.launch {
            _uiState.value = ComicDetailsUiState.Loading
            try {
                _uiState.value = ComicDetailsUiState.Loading
                val comic = comicRepository.getComicDetails(comicId)
                _uiState.value = ComicDetailsUiState.Success(comic)
            } catch (e: IOException) {
                _uiState.value = ComicDetailsUiState.Error("Failed to load comic details.")
            } catch (e: NoSuchElementException) {
                _uiState.value = ComicDetailsUiState.Error("Comic not found.")
            } catch (e: Exception) {
                _uiState.value = ComicDetailsUiState.Error("An unexpected error occurred.")
            }
        }
    }
}

/**
 * Sealed class representing the different UI states for comic details.
 *
 */
sealed class ComicDetailsUiState {
    object Loading : ComicDetailsUiState()
    data class Success(val comic: Comic) : ComicDetailsUiState()
    data class Error(val message: String) : ComicDetailsUiState()
}