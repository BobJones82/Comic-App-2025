package com.pas.comicapp.presentation.comiclist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pas.comicapp.data.repository.ComicRepository
import com.pas.comicapp.domain.model.Comic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class for handling comic list related operations.
 *
 * @property comicRepository
 */
@HiltViewModel
class ComicListViewModel @Inject constructor(
    private val comicRepository: ComicRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ComicListUiState>(ComicListUiState.Loading)
    val uiState: StateFlow<ComicListUiState> = _uiState

    init {
        fetchComics()
    }

    /**
     * Fetches the list of comics.
     *
     */
    fun fetchComics() {
        viewModelScope.launch {
            _uiState.value = ComicListUiState.Loading
            try {
                val comics = comicRepository.getComics()
                _uiState.value = ComicListUiState.Success(comics)
            } catch (e: Exception) {
                _uiState.value = ComicListUiState.Error("Failed to load comics: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }
}

/**
 * Sealed class representing the different UI states for comic list.
 *
 */
sealed class ComicListUiState {
    object Loading : ComicListUiState()
    data class Success(val comics: List<Comic>) : ComicListUiState()
    data class Error(val message: String) : ComicListUiState()
}