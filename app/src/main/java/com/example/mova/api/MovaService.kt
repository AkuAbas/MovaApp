package com.example.mova.api

import com.example.mova.model.ActorResponse
import com.example.mova.model.DetailResponse
import com.example.mova.model.MovieResponse
import com.example.mova.model.ReviewResponse
import com.example.mova.model.TvDetailsResponse
import com.example.mova.model.TvSeriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovaService {

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): Response<MovieResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedTvSeries(): Response<TvSeriesResponse>

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String): Response<MovieResponse>

    @GET("search/tv")
    suspend fun searchTvSeries(@Query("query") query: String): Response<TvSeriesResponse>

    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: String): Response<DetailResponse>

    @GET("tv/{id}")
    suspend fun getTvSeriesDetails(@Path("id") id: String): Response<TvDetailsResponse>

    @GET("movie/{id}/credits")
    suspend fun getMovieActors(@Path("id") id: String): Response<ActorResponse>

    @GET("tv/{id}/credits")
    suspend fun getTvActors(@Path("id") id: String): Response<ActorResponse>

    @GET("movie/{id}/recommendations")
    suspend fun getRelatedMovies(@Path("id") id: String): Response<MovieResponse>

    @GET("tv/{id}/recommendations")
    suspend fun getRelatedTvSeries(@Path("id") id: String): Response<MovieResponse>

    @GET("movie/{id}/reviews")
    suspend fun getMovieReviews(@Path("id") id: String): Response<ReviewResponse>

    @GET("tv/{id}/reviews")
    suspend fun getTvSeriesReviews(@Path("id") id: String): Response<ReviewResponse>
}