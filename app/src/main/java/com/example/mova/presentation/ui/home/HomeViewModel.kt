package com.example.mova.presentation.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova.api.NetworkResponse
import com.example.mova.di.MovaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MovaRepository) : ViewModel() {

    val popularState = MutableLiveData<MovieUiState>()
    val upcomingState = MutableLiveData<MovieUiState>()


    fun getPopularMovies() {
        viewModelScope.launch {
            repository.getPopularMovies().collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> popularState.value = MovieUiState.Loading

                    is NetworkResponse.Error -> it.message?.let {
                        popularState.value = MovieUiState.Error(it)
                    }

                    is NetworkResponse.Success -> {
                        popularState.value = it.data?.movies?.let {
                            MovieUiState.Success(it)
                        }

                        popularState.value = it.data?.movies?.let {
                            MovieUiState.ViewPager(it)
                        }
                    }
                }
            }
        }
    }

    fun getUpcomingMovies() {
        viewModelScope.launch {
            repository.getUpcomingMovies().collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> upcomingState.value = MovieUiState.Loading

                    is NetworkResponse.Error -> it.message?.let {
                        upcomingState.value = MovieUiState.Error(it)
                    }

                    is NetworkResponse.Success -> it.data?.movies?.let {
                        upcomingState.value = MovieUiState.Success(it)
                    }
                }
            }
        }
    }

}