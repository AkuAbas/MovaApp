package com.example.mova.presentation.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mova.base.BaseFragment
import com.example.mova.databinding.FragmentRelatedBinding
import com.example.mova.presentation.ui.home.MovieAdapter
import com.example.mova.presentation.ui.home.MovieUiState
import com.example.mova.utils.gone
import com.example.mova.utils.visible
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RelatedFragment(private val movieId: String, private val isMovie: Boolean) :
    BaseFragment<FragmentRelatedBinding>(FragmentRelatedBinding::inflate) {

    private val viewModel by viewModels<DetailViewModel>()
    private val adapter = MovieAdapter(true)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvRelated.adapter = adapter
        observeData()
        getRecommendations()
    }

    private fun observeData() {
        viewModel.relatedMovieState.observe(viewLifecycleOwner) {
            binding.animationViewPager.gone()
            when (it) {
                is MovieUiState.Loading -> binding.animationViewPager.visible()
                is MovieUiState.Error -> showFancyToast(it.message, FancyToast.ERROR)
                is MovieUiState.Success -> {
                    if (it.movieList.isNotEmpty()) {
                        adapter.submitList(it.movieList)
                    } else binding.textViewNoMovie.visible()
                }

                else -> {}
            }
        }
        viewModel.relatedTvState.observe(viewLifecycleOwner) {
            binding.animationViewPager.gone()
            when (it) {
                is MovieUiState.Loading -> binding.animationViewPager.visible()
                is MovieUiState.Error -> showFancyToast(it.message, FancyToast.ERROR)
                is MovieUiState.Success -> {
                    if (it.movieList.isNotEmpty()) {
                        adapter.submitList(it.movieList)
                    } else binding.textViewNoMovie.visible()
                }

                else -> {}
            }
        }
    }

    private fun getRecommendations() {
        if (isMovie) {
            viewModel.getRelatedMovies(movieId)
        } else {
            viewModel.getRelatedTvSeries(movieId)
        }
    }
}