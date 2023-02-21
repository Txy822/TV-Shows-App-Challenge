package com.tes.android.projects.tvshowsapp.presentation.search_shows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.tes.android.projects.tvshowsapp.core.mapper.toShowListing
import com.tes.android.projects.tvshowsapp.data.repository.FakeRepository
import com.tes.android.projects.tvshowsapp.data.repository.getDummyResponse
import com.tes.android.projects.tvshowsapp.domain.use_case.FavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SearchShowsViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutionRule: TestRule = InstantTaskExecutorRule()

    private lateinit var searchShowsViewModel: SearchShowsViewModel
    private lateinit var fakeRepository: FakeRepository
    private lateinit var useCase:FavoriteUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
         Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeRepository()
        useCase= FavoriteUseCase(fakeRepository)
        searchShowsViewModel = SearchShowsViewModel(fakeRepository, testDispatcher,useCase )
    }

    @Test
    fun `success data response from api`() = runTest {
        searchShowsViewModel.onEvent(SearchShowsEvent.LoadShows)
        searchShowsViewModel.uiState.test {
            assertEquals(awaitItem(),  SearchShowsState(isLoading =false)) //initialise state
            assertEquals(awaitItem(),  SearchShowsState(isLoading =true)) //actual loading state
            assertEquals(getDummyResponse().map { it.toShowListing() }, awaitItem().shows) //emit  data
        }
    }
    @Test
    fun `failure data response from api`()= runTest{
        fakeRepository.shouldEmitError=true
        searchShowsViewModel.onEvent(SearchShowsEvent.LoadShows)
        searchShowsViewModel.uiState.test {
            awaitItem() // //initialise state
            awaitItem() //actual loading state
            assertEquals("Error message", awaitItem().error)  //error case
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}


