package com.tes.android.projects.tvshowsapp.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.tes.android.projects.tvshowsapp.data.local.dao.FavoriteShowDao
import com.tes.android.projects.tvshowsapp.data.local.dao.TvShowDao
import com.tes.android.projects.tvshowsapp.data.local.datasource.LocalDataSource
import com.tes.android.projects.tvshowsapp.data.local.datasource.LocalDataSourceImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import com.tes.android.projects.tvshowsapp.data.local.db.TvShowDatabase
import com.tes.android.projects.tvshowsapp.data.remote.apiservice.TvShowApi
import com.tes.android.projects.tvshowsapp.data.remote.datasource.RemoteDataSource
import com.tes.android.projects.tvshowsapp.data.remote.datasource.RemoteDataSourceImpl
import com.tes.android.projects.tvshowsapp.data.repository.ShowRepositoryImpl
import com.tes.android.projects.tvshowsapp.domain.repository.ShowRepository
import com.tes.android.projects.tvshowsapp.domain.use_case.AddFavoriteUseCase
import com.tes.android.projects.tvshowsapp.domain.use_case.DeleteFavoriteUseCase
import com.tes.android.projects.tvshowsapp.domain.use_case.GetFavoriteUseCase
import com.tes.android.projects.tvshowsapp.domain.use_case.GetShowListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)

class AppModule {
    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .build()

    @Provides
    fun providesRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(TvShowApi.BASE_URL)
        .build()

    @Provides
    fun providesShowsApi(retrofit: Retrofit): TvShowApi = retrofit.create(TvShowApi::class.java)

    @Provides
    fun providesRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): ShowRepository {
        return ShowRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Provides
    fun provideAddFavoriteUseCase(repository: ShowRepository): AddFavoriteUseCase {
        return AddFavoriteUseCase(repository)
    }

    @Provides
    fun provideDeleteFavoriteUseCase(repository: ShowRepository): DeleteFavoriteUseCase {
        return DeleteFavoriteUseCase(repository)
    }

    @Provides
    fun provideGetShowListUseCase(repository: ShowRepository): GetShowListUseCase {
        return GetShowListUseCase(repository)
    }
    @Provides
    fun provideGetFavoriteUseCase(repository: ShowRepository): GetFavoriteUseCase {
        return GetFavoriteUseCase(repository)
    }

    @Provides
    fun providesDatabase(app: Application): TvShowDatabase {
        return Room.databaseBuilder(
            app,
            TvShowDatabase::class.java,
            "showdb.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideShowDao(database: TvShowDatabase) = database.showDao()

    @Provides
    fun provideFavoriteShowDao(database: TvShowDatabase) = database.favoriteShowDao()

    @Provides
    fun getRemoteDS(showApi: TvShowApi): RemoteDataSource {
        return RemoteDataSourceImpl(showApi)
    }
    @Provides
    fun getLocalDS(showDao: TvShowDao,
                   favoriteDao:FavoriteShowDao
    ): LocalDataSource {
        return LocalDataSourceImpl(showDao,favoriteDao)
    }

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }
    @Provides
    fun providesMainDispatcher():CoroutineDispatcher=Dispatchers.Main
}