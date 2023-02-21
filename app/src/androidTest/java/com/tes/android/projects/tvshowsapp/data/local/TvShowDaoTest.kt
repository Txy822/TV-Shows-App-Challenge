package com.tes.android.projects.tvshowsapp.data.local


import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.tes.android.projects.tvshowsapp.data.local.dao.FavoriteShowDao
import com.tes.android.projects.tvshowsapp.data.local.dao.TvShowDao
import com.tes.android.projects.tvshowsapp.data.local.db.TvShowDatabase
import com.tes.android.projects.tvshowsapp.data.local.entity.FavoriteShowListingEntity
import com.tes.android.projects.tvshowsapp.data.local.entity.ShowListingEntity
import com.tes.android.projects.tvshowsapp.data.remote.dto.Image
import com.tes.android.projects.tvshowsapp.data.remote.dto.Rating
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TvShowDaoTest {

    private lateinit var database: TvShowDatabase
    private lateinit var showDao: TvShowDao
    private lateinit var favoriteShowDao: FavoriteShowDao

    private lateinit var showListingEntityList: MutableList<ShowListingEntity>
    private lateinit var favoriteShowListingEntityList:MutableList<FavoriteShowListingEntity>

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TvShowDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        showDao = database.showDao()
        favoriteShowDao=database.favoriteShowDao()
        showListingEntityList = mutableListOf()
        favoriteShowListingEntityList= mutableListOf()
    }


    @Test
    fun insertShowListingToDBTest() = runBlocking {
        val imageOne = Image()
        val imageTwo = Image()
        val ratingOne= Rating()
        val ratingTwo= Rating()

        val showOne =
            ShowListingEntity("name1", "type1", 0, imageOne, "pre", "status1", "summary1", id = 1, rating = ratingOne)
        val showTwo =
            ShowListingEntity("name2", "type2", 0, imageTwo, "pre", "status2", "summary2", id = 2, rating = ratingTwo)
        showListingEntityList.add(showOne)
        showListingEntityList.add(showTwo)
        showDao.insertShowListings(showListingEntityList)

        assertThat(showDao.getSingleShowFromDB("name1")).isEqualTo(showOne)
        assertThat(showDao.getSingleShowFromDB("name2")).isEqualTo(showTwo)
    }

    @Test
    fun clearShowTest() = runBlocking {
        val imageOne = Image()
        val ratingOne= Rating()
        val showOne =
            ShowListingEntity("name1", "type1", 0, imageOne, "pre", "status1", "summary1", id = 1, rating = ratingOne)

        showListingEntityList.add(showOne)
        showDao.insertShowListings(showListingEntityList)
        showDao.clearShowListings()

        assertThat(showDao.getSingleShowFromDB("name1")).isEqualTo(null)
    }

    @Test
    fun getSingleSearchTest() = runBlocking {
        val imageOne = Image()
        val ratingOne= Rating()
        val showOne =
            ShowListingEntity("name1", "type1", 0, imageOne, "pre", "status1", "summary1", id = 1, rating = ratingOne)

        showListingEntityList.add(showOne)
        showDao.insertShowListings(showListingEntityList)

        assertThat(showDao.getSingleShowFromDB("name1")).isEqualTo(showOne)

    }

   @Test
   fun deleteFavoriteByIdTest(): Unit = runBlocking {
       val favoriteId=1
       val imageOne = Image()
       val ratingOne= Rating()

       val favoriteShowOne =
           FavoriteShowListingEntity("name1", "type1", 0, imageOne, "pre", "status1", "summary1", id = favoriteId, rating = ratingOne)

       favoriteShowDao.insertFavoriteShow(favoriteShowOne)

       favoriteShowDao.deleteFavoriteById(favoriteId)

       assertThat(favoriteShowDao.getAllFavorites() .isEmpty())

    }


    @Test
    fun getAllFavoritesFromDbTest() = runBlocking {
        val imageOne = Image()
        val ratingOne= Rating()

        val favoriteShowOne =
            FavoriteShowListingEntity("name1", "type1", 0, imageOne, "pre", "status1", "summary1", id = 1, rating = ratingOne)
        favoriteShowDao.insertFavoriteShow(favoriteShowOne)

        assertThat(favoriteShowDao.getAllFavorites()[0]) .isEqualTo(favoriteShowOne)

    }
    @Test
     fun insertFavoriteShowToDbTest()= runBlocking{
        val imageOne = Image()
        val ratingOne= Rating()

        val favoriteShowOne =
            FavoriteShowListingEntity("name1", "type1", 0, imageOne, "pre", "status1", "summary1", id = 1, rating = ratingOne)
        favoriteShowDao.insertFavoriteShow(favoriteShowOne)

        assertThat(favoriteShowDao.getAllFavorites()[0]) .isEqualTo(favoriteShowOne)

     }

    @After
    fun teardown() {
        database.close()
    }
}