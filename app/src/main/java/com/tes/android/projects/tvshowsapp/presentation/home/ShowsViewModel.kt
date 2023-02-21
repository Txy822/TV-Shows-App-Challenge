package com.tes.android.projects.tvshowsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tes.android.projects.tvshowsapp.domain.model.ShowDetail
import com.tes.android.projects.tvshowsapp.domain.repository.ShowRepository
import com.tes.android.projects.tvshowsapp.domain.use_case.FavoriteUseCase
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
class ShowsViewModel @Inject constructor(
    private val repository: ShowRepository,
    private val dispatcher: CoroutineDispatcher,
    private val favoriteUseCase: FavoriteUseCase
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ShowsState())

        val uiState: StateFlow<ShowsState> = _uiState.asStateFlow()

        fun onEvent(event: ShowsEvent) {
            when (event) {
                is ShowsEvent.LoadShows -> {
                    getShowListings()
                }
                is ShowsEvent.OnFavoriteSelected -> {
                    _uiState.value = ShowsState(show = event.show)
                    //_uiState.update { it.copy(show = event.show) }
                    addFavorite()

                }
                is ShowsEvent.DeleteFavorite-> {
                     // _uiState.value = _uiState.value.copy(id = event.id)
                    _uiState.update { it.copy(id=event.id) }
                    deleteFavorite()
                }
            }
        }

        private fun deleteFavorite(
            id: Int = _uiState.value.id
        ) {
            viewModelScope.launch(dispatcher) {
                repository.deleteFavoriteById(id)
            }
        }

        private fun addFavorite(
            show: ShowDetail = _uiState.value.show
        ) {
            viewModelScope.launch(dispatcher) {
                favoriteUseCase.addFavorite(show)
                //repository.insertFavoriteShowToDb(show)
                getShowListings()
            }
        }

        private fun getShowListings(
            query: String = "",

            fetchFromRemote: Boolean = false
        ) {
            viewModelScope.launch(dispatcher) {
                repository.getShowListings(fetchFromRemote, query)
                    .collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                result.data?.let { listings ->
                                    // copy of  current state so that we can change the listing
                                    // _uiState.value = _uiState.value.copy(shows = listings)
                                    _uiState.update { it.copy(shows=listings) }
                                }
                                // _uiState.value = _uiState.value.copy()
                            }
                            is Resource.Error -> {
                                // _uiState.value = _uiState.value.copy(error = "Error message")
                                _uiState.update { it.copy(error = "Error message") }
                            }
                            is Resource.Loading -> {
                                // _uiState.value = _uiState.value.copy(isLoading = result.isLoading)
                                _uiState.update { it.copy(isLoading = result.isLoading) }
                            }
                        }
                    }
            }
        }
    }