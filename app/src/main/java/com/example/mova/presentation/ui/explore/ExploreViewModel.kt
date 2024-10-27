package com.example.mova.presentation.ui.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova.api.NetworkResponse
import com.example.mova.di.MovaRepository
import com.example.mova.model.TvSeries
import com.example.mova.presentation.ui.home.MovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(private val repository: MovaRepository) : ViewModel() {

    val movieSearchState = MutableLiveData<MovieUiState>()
    val tvSearchState = MutableLiveData<TvSeriesUiState>()
    val selectedTabIndex = MutableLiveData<Int>()
    val tvQuery = MutableLiveData<String>()
    val movieQuery = MutableLiveData<String>()
    val ratedMovieState = MutableLiveData<MovieUiState>()
    val ratedTvSeriesState = MutableLiveData<TvSeriesUiState>()

    fun getTopRatedMovies() {
        viewModelScope.launch {
            repository.getRatedMovies().collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> ratedMovieState.value = MovieUiState.Loading
                    is NetworkResponse.Error -> ratedMovieState.value = it.message?.let {
                        MovieUiState.Error(it)
                    }

                    is NetworkResponse.Success -> ratedMovieState.value = it.data?.movies?.let {
                        MovieUiState.Success(it)
                    }
                }
            }
        }
    }

    fun getTopRatedTvSeries() {
        viewModelScope.launch {
            repository.getRatedTvSeries().collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> ratedTvSeriesState.value = TvSeriesUiState.Loading
                    is NetworkResponse.Error -> ratedTvSeriesState.value = it.message?.let {
                        TvSeriesUiState.Error(it)
                    }

                    is NetworkResponse.Success -> ratedTvSeriesState.value =
                        it.data?.tvSeries?.let {
                            TvSeriesUiState.Success(it)
                        }
                }
            }
        }
    }


    fun searchMovies(query: String) {
        viewModelScope.launch {
            repository.searchMovies(query).collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> movieSearchState.value = MovieUiState.Loading
                    is NetworkResponse.Error -> movieSearchState.value = it.message?.let {
                        MovieUiState.Error(it)
                    }

                    is NetworkResponse.Success -> {
                        movieSearchState.value = it.data?.movies?.let {
                            MovieUiState.Success(it)
                        }
                    }
                }
            }
        }
    }

    fun searchTvSeries(query: String) {
        viewModelScope.launch {
            repository.searchTvSeries(query).collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> tvSearchState.value = TvSeriesUiState.Loading
                    is NetworkResponse.Error -> tvSearchState.value = it.message?.let {
                        TvSeriesUiState.Error(it)
                    }

                    is NetworkResponse.Success -> tvSearchState.value = it.data?.tvSeries?.let {
                        TvSeriesUiState.Success(it)
                    }
                }
            }
        }
    }

    fun setSelectedTabIndex(currentTab: Int) {
        selectedTabIndex.value = currentTab
    }

    fun saveTvQuery(query: String) {
        tvQuery.value = query
    }

    fun saveMovieQuery(query: String) {
        movieQuery.value = query
    }

    sealed class TvSeriesUiState {
        data object Loading : TvSeriesUiState()
        class Error(val message: String) : TvSeriesUiState()
        class Success(val tvList: List<TvSeries>) : TvSeriesUiState()
    }

}