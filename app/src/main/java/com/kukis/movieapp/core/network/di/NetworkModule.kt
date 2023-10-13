package com.kukis.movieapp.core.network.di

import com.kukis.movieapp.BuildConfig.BASE_URL
import com.kukis.movieapp.core.interceptors.AuthInterceptor
import com.kukis.movieapp.details.data.DetailRepositoryImpl
import com.kukis.movieapp.details.data.network.DetailClient
import com.kukis.movieapp.details.domain.DetailRepository
import com.kukis.movieapp.home.data.network.HomeClient
import com.kukis.movieapp.movie.data.MovieRepositoryImpl
import com.kukis.movieapp.movie.data.network.MovieClient
import com.kukis.movieapp.movie.domain.MovieRepository
import com.kukis.movieapp.search.data.SearchRepositoryImpl
import com.kukis.movieapp.search.data.network.SearchClient
import com.kukis.movieapp.search.domain.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun providerOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(authInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideLoginClient(retrofit: Retrofit): HomeClient {
        return retrofit.create(HomeClient::class.java)
    }

    @Provides
    @Singleton
    fun provideDetailApiService(retrofit: Retrofit): DetailClient {
        return retrofit.create(DetailClient::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchApiService(retrofit: Retrofit): SearchClient {
        return retrofit.create(SearchClient::class.java)
    }
    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit): MovieClient {
        return retrofit.create(MovieClient::class.java)
    }

    @Provides
    fun providesDetailsRepository(apiService: DetailClient): DetailRepository {
        return DetailRepositoryImpl(apiService)
    }

    @Provides
    fun providesSearchRepository(apiService: SearchClient): SearchRepository {
        return SearchRepositoryImpl(apiService)
    }

    @Provides
    fun providesMovieRepository(apiService: MovieClient): MovieRepository {
        return MovieRepositoryImpl(apiService)
    }
}