package com.tes.android.projects.tvshowsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tes.android.projects.tvshowsapp.data.local.entity.ShowListingEntity

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShowListings(showListingEntity: List<ShowListingEntity>)

    @Query("DELETE FROM show_listing_table")
    suspend fun clearShowListings()

    @Query(
        """
            SELECT * 
            FROM show_listing_table
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
                UPPER(:query) == name
        """
    )
    suspend fun searchShowListing(query: String): List<ShowListingEntity>

    @Query("""SELECT * FROM show_listing_table WHERE show_listing_table.name ==:showName""")
    suspend fun getSingleShowFromDB(showName: String): ShowListingEntity

}