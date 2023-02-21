package com.tes.android.projects.tvshowsapp.presentation.search_shows

import com.tes.android.projects.tvshowsapp.data.remote.dto.Image
import com.tes.android.projects.tvshowsapp.data.remote.dto.Rating
import com.tes.android.projects.tvshowsapp.domain.model.ShowDetail

data class SearchShowsState(
    val shows: List<ShowDetail> = emptyList(),
    val isLoading:Boolean =false,
    val isRefreshing:Boolean=false,
    val searchQuery:String="",
    val id:Int=0,
    val show: ShowDetail=ShowDetail(0,"","",0,image= Image(),"","","", rating = Rating()),
    val error:String=""
    )
