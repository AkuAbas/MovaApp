package com.example.mova.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.mova.databinding.FragmentHomeBinding
import com.example.mova.model.Movie
import com.example.mova.utils.gone
import com.example.mova.utils.visible
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()
    private val topRatedAdapter = MovieAdapter()
    private val upcomingAdapter = MovieAdapter()
    private val viewPagerAdapter = HomePagerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapters()
        observeData()
        getMovies()
        setupClicks()
    }


    private fun observeData() {
        viewModel.popularState.observe(viewLifecycleOwner) {
            binding.progressBarTop10.gone()
            when (it) {
                is MovieUiState.Loading -> binding.progressBarTop10.visible()
                is MovieUiState.Error -> showFancyToast(it.message, FancyToast.ERROR)
                is MovieUiState.Success -> {
                    topRatedAdapter.submitList(
                        it.movieList.take(
                            10
                        )
                    )
                    val popularList = it.movieList
                    goToSeeAllTopRated(popularList.toTypedArray())
                }

                is MovieUiState.ViewPager -> viewPagerAdapter.submitList(it.details.take(3))
                else -> Unit
            }
        }
        viewModel.upcomingState.observe(viewLifecycleOwner) {
            binding.progressBarNew.gone()
            when (it) {
                is MovieUiState.Loading -> binding.progressBarNew.visible()
                is MovieUiState.Error -> showFancyToast(
                    it.message,
                    FancyToast.ERROR
                )

                is MovieUiState.Success -> {
                    upcomingAdapter.submitList(
                        it.movieList.take(
                            10
                        )
                    )
                    val upcomingList = it.movieList
                    goToSeeAllUpcoming(
                        upcomingList.toTypedArray()
                    )
                }

                else -> Unit
            }
        }
    }

    private fun goToSeeAllUpcoming(list: Array<Movie>) {
        binding.buttonSeeAllUpcoming.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToViewAllFragment(
                    list, "upcoming"
                )
            )
        }
    }

    private fun goToSeeAllTopRated(list: Array<Movie>) {
        binding.buttonSeeAllPopular.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToViewAllFragment(
                    list, "topRated"
                )
            )
        }
    }

    private fun setAdapters() {
        binding.rvTop10.adapter = topRatedAdapter
        binding.rvNewReleases.adapter = upcomingAdapter
        binding.viewPagerHome.adapter = viewPagerAdapter
    }

    private fun setupClicks() {
        viewPagerAdapter.onClick = {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNotificationFragment())
        }
        topRatedAdapter.onClick = { id, _ ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    true, id
                )
            )
        }
        upcomingAdapter.onClick = { id, _ ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    true, id
                )
            )
        }
    }

    private fun getMovies() {
        viewModel.getPopularMovies()
        viewModel.getUpcomingMovies()
    }


}