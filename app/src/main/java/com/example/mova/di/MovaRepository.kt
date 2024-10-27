package com.example.mova.di

import com.example.mova.api.MovaService
import com.example.mova.api.NetworkResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import retrofit2.Response
import javax.inject.Inject

class MovaRepository @Inject constructor(
    private val service: MovaService,
    private val fireStore: FirebaseFirestore
) {

    suspend fun getPopularMovies() = safeApiRequest {
        service.getPopularMovies()
    }

    suspend fun getUpcomingMovies() = safeApiRequest {
        service.getUpcomingMovies()
    }

    suspend fun getRatedMovies() = safeApiRequest {
        service.getTopRatedMovies()
    }

    suspend fun getRatedTvSeries() = safeApiRequest {
        service.getTopRatedTvSeries()
    }

    suspend fun searchMovies(query: String) = safeApiRequest {
        service.searchMovies(query)
    }

    suspend fun searchTvSeries(query: String) = safeApiRequest {
        service.searchTvSeries(query)
    }

    suspend fun getMovieDetails(id: String) = safeApiRequest {
        service.getMovieDetails(id)
    }

    suspend fun getTvSeriesDetails(id: String) = safeApiRequest {
        service.getTvSeriesDetails(id)
    }

    suspend fun getMovieActors(id: String) = safeApiRequest {
        service.getMovieActors(id)
    }

    suspend fun getTvActors(id: String) = safeApiRequest {
        service.getTvActors(id)
    }

    suspend fun getRelatedMovies(id: String) = safeApiRequest {
        service.getRelatedMovies(id)
    }

    suspend fun getRelatedTvSeries(id: String) = safeApiRequest {
        service.getRelatedTvSeries(id)
    }

    suspend fun getMovieReviews(id: String) = safeApiRequest {
        service.getMovieReviews(id)
    }

    suspend fun getTvSeriesReviews(id: String) = safeApiRequest {
        service.getTvSeriesReviews(id)
    }

    suspend fun getNotifications() = fireStore.collection("movieNotifications")
        .get().await()


    private suspend fun <T> safeApiRequest(apicall: suspend () -> Response<T>): Flow<NetworkResponse<T>> =
        flow<NetworkResponse<T>> {
            emit(NetworkResponse.Loading())
            try {
                val response = apicall.invoke()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(NetworkResponse.Success(it))
                    } ?: run { emit(NetworkResponse.Error("is empty.")) }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    emit(NetworkResponse.Error(errorBody))
                }
            } catch (e: Exception) {
                emit(NetworkResponse.Error(e.localizedMessage.orEmpty()))
            }
        }.flowOn(IO)
}