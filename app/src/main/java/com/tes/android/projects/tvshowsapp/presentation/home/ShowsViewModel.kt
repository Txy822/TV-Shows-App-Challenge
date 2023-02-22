package com.tes.android.projects.tvshowsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tes.android.projects.tvshowsapp.domain.model.ShowDetail
import com.tes.android.projects.tvshowsapp.domain.repository.ShowRepository
import com.tes.android.projects.tvshowsapp.domain.use_case.AddFavoriteUseCase
import com.tes.android.projects.tvshowsapp.domain.use_case.DeleteFavoriteUseCase
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
class ShowsViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val getShowListUseCase: GetShowListUseCase

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
                addFavorite()

            }
            is ShowsEvent.DeleteFavorite -> {
                _uiState.update { it.copy(id = event.id) }
                deleteFavorite()
            }
        }
    }

    private fun deleteFavorite(
        id: Int = _uiState.value.id
    ) {
        viewModelScope.launch(dispatcher) {
            deleteFavoriteUseCase.deleteFavorite(id)
        }
    }

    private fun addFavorite(
        show: ShowDetail = _uiState.value.show
    ) {
        viewModelScope.launch(dispatcher) {
            addFavoriteUseCase.addFavorite(show)
            getShowListings()
        }
    }

    private fun getShowListings(
        query: String = "",
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch(dispatcher) {
            getShowListUseCase.getShowList(fetchFromRemote = fetchFromRemote, query = query)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                _uiState.update { it.copy(shows = listings) }
                            }
                            _uiState.value = _uiState.value.copy()
                        }
                        is Resource.Error -> {
                            _uiState.update { it.copy(error = "Error message") }
                        }
                        is Resource.Loading -> {
                            _uiState.update { it.copy(isLoading = result.isLoading) }
                        }
                    }
                }
        }
    }
}