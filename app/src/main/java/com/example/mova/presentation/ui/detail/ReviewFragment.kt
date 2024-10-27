package com.example.mova.presentation.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.mova.base.BaseFragment
import com.example.mova.databinding.FragmentReviewBinding
import com.example.mova.utils.gone
import com.example.mova.utils.visible
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReviewFragment(private val movieId: String, private val isMovie: Boolean) :
    BaseFragment<FragmentReviewBinding>(FragmentReviewBinding::inflate) {

    private val viewModel by viewModels<DetailViewModel>()
    private val adapter = ReviewAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvReviews.adapter = adapter
        observeData()
        getReviews()

    }

    private fun observeData() {
        viewModel.movieReviewState.observe(viewLifecycleOwner) {
            binding.animationView.gone()
            when (it) {
                is DetailViewModel.ReviewUiState.ReviewLoading -> binding.animationView.visible()
                is DetailViewModel.ReviewUiState.ReviewError -> showFancyToast(
                    it.message,
                    FancyToast.ERROR
                )

                is DetailViewModel.ReviewUiState.ReviewSuccess -> {
                    if (it.reviewList.isNotEmpty()) adapter.submitList(it.reviewList) else binding.textViewNoReview.visible()
                }
            }
        }
        viewModel.tvReviewState.observe(viewLifecycleOwner) {
            binding.animationView.gone()
            when (it) {
                is DetailViewModel.ReviewUiState.ReviewLoading -> binding.animationView.visible()
                is DetailViewModel.ReviewUiState.ReviewError -> showFancyToast(
                    it.message,
                    FancyToast.ERROR
                )

                is DetailViewModel.ReviewUiState.ReviewSuccess -> {
                    if (it.reviewList.isNotEmpty()) adapter.submitList(it.reviewList) else binding.textViewNoReview.visible()
                }
            }
        }
    }

    private fun getReviews() {
        if (isMovie) {
            viewModel.getMovieReviews(movieId)
        } else {
            viewModel.getTvSeriesReviews(movieId)
        }
    }

}