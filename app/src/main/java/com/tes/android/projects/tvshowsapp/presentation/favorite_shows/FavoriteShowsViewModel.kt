package com.tes.android.projects.tvshowsapp.presentation.favorite_shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tes.android.projects.tvshowsapp.domain.repository.ShowRepository
import com.tes.android.projects.tvshowsapp.domain.use_case.AddFavoriteUseCase
import com.tes.android.projects.tvshowsapp.domain.use_case.DeleteFavoriteUseCase
import com.tes.android.projects.tvshowsapp.domain.use_case.GetFavoriteUseCase
import com.tes.android.projects.tvshowsapp.domain.use_case.GetShowListUseCase
import com.tes.android.projects.tvshowsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteShowsViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val  getFavoriteUseCase: GetFavoriteUseCase

) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoriteShowsUiState())

    val uiState: StateFlow<FavoriteShowsUiState> = _uiState.asStateFlow()

    fun onEvent(event: FavoriteShowsEvent) {
        when (event) {

            is FavoriteShowsEvent.OnDeleteSelected -> {
                _uiState.update { it.copy(id=event.id) }

                viewModelScope.launch {
                    deleteFavorite()
                }
            }
            is FavoriteShowsEvent.LoadFavoriteShows-> getFavoriteShowListings()
        }
    }

    private fun deleteFavorite(
        id: Int = _uiState.value.id
    ) {
        viewModelScope.launch(dispatcher) {
            deleteFavoriteUseCase.deleteFavorite(id)
            getFavoriteShowListings()
        }

    }

    private fun getFavoriteShowListings(
    ) {
        viewModelScope.launch(dispatcher) {
            getFavoriteUseCase.getFavorites()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                _uiState.value = _uiState.value.copy(favoriteShows = listings, isLoading = false)
                            }
                            _uiState.value = _uiState.value.copy()
                        }
                        is Resource.Error -> {
                            _uiState.value = _uiState.value.copy(error = "Error occurred")
                        }
                        is Resource.Loading -> {
                            _uiState.value = _uiState.value.copy(isLoading = true)
                        }
                    }
                }
        }
    }
}
