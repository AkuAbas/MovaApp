package com.example.mova.presentation.ui.home

import com.example.mova.model.Movie

sealed class MovieUiState {

    data object Loading : MovieUiState()
    data class Success(val movieList: List<Movie>) : MovieUiState()
    data class Error(val message: String) : MovieUiState()
    data class ViewPager(val details: List<Movie>) : MovieUiState()

}