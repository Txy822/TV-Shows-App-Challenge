package com.tes.android.projects.tvshowsapp.presentation.search_shows

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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchShowsViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val getShowListUseCase: GetShowListUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchShowsState())

    val uiState: StateFlow<SearchShowsState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun onEvent(event: SearchShowsEvent) {
        when (event) {
            is SearchShowsEvent.Refresh -> {
                getShowListings(fetchFromRemote = true)
            }
            is SearchShowsEvent.OnSearchQueryChange -> {
                _uiState.update { it.copy(searchQuery = event.query) }

                searchJob = viewModelScope.launch {
                    delay(500L)
                    getShowListings()
                }
            }
            is SearchShowsEvent.OnFavoriteSelected -> {
                _uiState.update { it.copy(show = event.show) }
                addFavorite()
            }
            is SearchShowsEvent.DeleteFavorite-> {
                _uiState.update { it.copy(id=event.id) }
                deleteFavorite()
            }
            is SearchShowsEvent.LoadShows -> {
                getShowListings()
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
        }
    }

    private fun getShowListings(
        query: String = _uiState.value.searchQuery.lowercase(),

        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch(dispatcher) {
            getShowListUseCase.getShowList(fetchFromRemote=fetchFromRemote, query=query)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                _uiState.update { it.copy(shows=listings) }
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