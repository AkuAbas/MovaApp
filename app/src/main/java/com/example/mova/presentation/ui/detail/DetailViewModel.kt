package com.example.mova.presentation.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova.api.NetworkResponse
import com.example.mova.di.MovaRepository
import com.example.mova.local.ListedContent
import com.example.mova.local.LocalRepository
import com.example.mova.model.ActorResponse
import com.example.mova.model.DetailResponse
import com.example.mova.model.Review
import com.example.mova.model.TvDetailsResponse
import com.example.mova.presentation.ui.home.MovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovaRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    val movieDetailState = MutableLiveData<MovieDetailUiState>()
    val tvDetailState = MutableLiveData<TvDetailUiState>()
    val movieActorState = MutableLiveData<ActorUiState>()
    val tvActorState = MutableLiveData<ActorUiState>()
    val relatedMovieState = MutableLiveData<MovieUiState>()
    val relatedTvState = MutableLiveData<MovieUiState>()
    val movieReviewState = MutableLiveData<ReviewUiState>()
    val tvReviewState = MutableLiveData<ReviewUiState>()
    val isContentInList = MutableLiveData<Int>()


    fun getMovieDetails(id: String) {
        viewModelScope.launch {
            repository.getMovieDetails(id).collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> movieDetailState.value =
                        MovieDetailUiState.DetailLoading

                    is NetworkResponse.Error -> movieDetailState.value = it.message?.let {
                        MovieDetailUiState.DetailError(it)
                    }

                    is NetworkResponse.Success -> movieDetailState.value = it.data?.let {
                        MovieDetailUiState.DetailSuccess(it)
                    }
                }

            }
        }
    }

    fun getTvSeriesDetails(id: String) {
        viewModelScope.launch {
            repository.getTvSeriesDetails(id).collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> tvDetailState.value =
                        TvDetailUiState.TvDetailLoading

                    is NetworkResponse.Error -> it.message?.let {
                        tvDetailState.value = TvDetailUiState.TvDetailError(it)
                    }

                    is NetworkResponse.Success -> it.data?.let {
                        tvDetailState.value = TvDetailUiState.TvDetailSuccess(it)
                    }
                }

            }
        }
    }

    fun getMovieActors(id: String) {
        viewModelScope.launch {
            repository.getMovieActors(id).collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> movieActorState.value = ActorUiState.ActorLoading
                    is NetworkResponse.Error -> movieActorState.value = it.message?.let {
                        ActorUiState.ActorError(it)
                    }

                    is NetworkResponse.Success -> movieActorState.value = it.data?.let {
                        ActorUiState.ActorSuccess(it)
                    }
                }
            }
        }
    }

    fun getTvActors(id: String) {
        viewModelScope.launch {
            repository.getTvActors(id).collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> tvActorState.value = ActorUiState.ActorLoading
                    is NetworkResponse.Error -> tvActorState.value = it.message?.let {
                        ActorUiState.ActorError(it)
                    }

                    is NetworkResponse.Success -> tvActorState.value = it.data?.let {
                        ActorUiState.ActorSuccess(it)
                    }
                }
            }
        }
    }

    fun getRelatedMovies(id: String) {
        viewModelScope.launch {
            repository.getRelatedMovies(id).collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> relatedMovieState.value = MovieUiState.Loading
                    is NetworkResponse.Error -> it.message?.let {
                        relatedMovieState.value = MovieUiState.Error(it)
                    }

                    is NetworkResponse.Success -> it.data?.movies?.let {
                        relatedMovieState.value = MovieUiState.Success(it)

                    }
                }
            }
        }
    }

    fun getRelatedTvSeries(id: String) {
        viewModelScope.launch {
            repository.getRelatedTvSeries(id).collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> relatedTvState.value = MovieUiState.Loading
                    is NetworkResponse.Error -> it.message?.let {
                        relatedTvState.value = MovieUiState.Error(it)
                    }

                    is NetworkResponse.Success -> it.data?.movies?.let {
                        relatedTvState.value = MovieUiState.Success(it)

                    }
                }
            }
        }
    }

    fun getMovieReviews(id: String) {
        viewModelScope.launch {
            repository.getMovieReviews(id).collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> movieReviewState.value =
                        ReviewUiState.ReviewLoading

                    is NetworkResponse.Error -> movieReviewState.value = it.message?.let {
                        ReviewUiState.ReviewError(it)
                    }

                    is NetworkResponse.Success -> movieReviewState.value = it.data?.reviews?.let {
                        ReviewUiState.ReviewSuccess(it)
                    }
                }
            }
        }
    }

    fun getTvSeriesReviews(id: String) {
        viewModelScope.launch {
            repository.getTvSeriesReviews(id).collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> tvReviewState.value =
                        ReviewUiState.ReviewLoading

                    is NetworkResponse.Error -> tvReviewState.value = it.message?.let {
                        ReviewUiState.ReviewError(it)
                    }

                    is NetworkResponse.Success -> tvReviewState.value = it.data?.reviews?.let {
                        ReviewUiState.ReviewSuccess(it)
                    }
                }
            }
        }
    }

    fun addListedContentId(listedContent: ListedContent) {
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.addContentId(listedContent)
        }
    }

    fun deleteListedContentId(listedContent: ListedContent) {
        viewModelScope.launch {
            localRepository.deleteContentId(listedContent)
        }
    }

    fun isContentInList(detailId: String) {
        viewModelScope.launch {
            val response = localRepository.isContentInList(detailId)
            isContentInList.value = response
        }
    }


    sealed class MovieDetailUiState {
        data object DetailLoading : MovieDetailUiState()
        class DetailError(val message: String) : MovieDetailUiState()
        class DetailSuccess(val movieDetail: DetailResponse) : MovieDetailUiState()
    }

    sealed class TvDetailUiState {
        data object TvDetailLoading : TvDetailUiState()
        class TvDetailError(val message: String) : TvDetailUiState()
        class TvDetailSuccess(val tvDetail: TvDetailsResponse) : TvDetailUiState()
    }


    sealed class ActorUiState {
        data object ActorLoading : ActorUiState()
        class ActorError(val message: String) : ActorUiState()
        class ActorSuccess(val detail: ActorResponse) : ActorUiState()
    }

    sealed class ReviewUiState {
        data object ReviewLoading : ReviewUiState()
        class ReviewError(val message: String) : ReviewUiState()
        class ReviewSuccess(val reviewList: List<Review>) : ReviewUiState()
    }
}
