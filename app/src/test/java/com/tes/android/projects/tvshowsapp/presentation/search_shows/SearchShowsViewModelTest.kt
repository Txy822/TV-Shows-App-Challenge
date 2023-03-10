package com.tes.android.projects.tvshowsapp.presentation.search_shows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.tes.android.projects.tvshowsapp.core.mapper.toShowListing
import com.tes.android.projects.tvshowsapp.data.repository.FakeRepository
import com.tes.android.projects.tvshowsapp.data.repository.getDummyResponse
import com.tes.android.projects.tvshowsapp.domain.use_case.AddFavoriteUseCase
import com.tes.android.projects.tvshowsapp.domain.use_case.DeleteFavoriteUseCase
import com.tes.android.projects.tvshowsapp.domain.use_case.GetShowListUseCase
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
    private lateinit var addFavoriteUseCase:AddFavoriteUseCase
    private lateinit var deleteFavoriteUseCase:DeleteFavoriteUseCase
    private lateinit var getShowListUseCase: GetShowListUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
         Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeRepository()
        addFavoriteUseCase= AddFavoriteUseCase(fakeRepository)
        deleteFavoriteUseCase= DeleteFavoriteUseCase(fakeRepository)
        getShowListUseCase= GetShowListUseCase(fakeRepository)
        searchShowsViewModel = SearchShowsViewModel(testDispatcher,addFavoriteUseCase,deleteFavoriteUseCase, getShowListUseCase)
    }

    @Test
    fun `success data response from api`() = runTest {
        searchShowsViewModel.onEvent(SearchShowsEvent.LoadShows)
        searchShowsViewModel.uiState.test {
            assertEquals(awaitItem(),  SearchShowsState(isLoading =false)) //initialise state
            assertEquals(getDummyResponse().map { it.toShowListing() }, awaitItem().shows) //emit  data
        }
    }
    @Test
    fun `failure data response from api`()= runTest{
        fakeRepository.shouldEmitError=true
        searchShowsViewModel.onEvent(SearchShowsEvent.LoadShows)
        searchShowsViewModel.uiState.test {
            awaitItem()  //initialise state
            // Await the change
            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals("Error message", awaitItem().error)  //error case
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}


