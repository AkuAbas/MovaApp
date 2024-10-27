package com.example.mova.presentation.ui.explore

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ListAdapter
import com.example.mova.base.BaseFragment
import com.example.mova.databinding.FragmentExploreBinding
import com.example.mova.presentation.ui.home.MovieAdapter
import com.example.mova.presentation.ui.home.MovieUiState
import com.example.mova.utils.gone
import com.example.mova.utils.visible
import com.google.android.material.tabs.TabLayout
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>(FragmentExploreBinding::inflate) {

    private val viewModel by viewModels<ExploreViewModel>()
    private var currentTab: Int = 0
    private val tvSearchAdapter = TvAdapter()
    private val movieSearchAdapter = MovieAdapter(true)
    private var savedTvQuery: String = ""
    private var savedMovieQuery: String = ""
    private var searchResult: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadDefaultContent()
        observeData()
        observeTab()
        setupClicks()
    }

    private fun loadDefaultContent() {
        if (currentTab == 0) {
            binding.rvExplore.adapter = movieSearchAdapter
            viewModel.getTopRatedMovies()
        } else {
            binding.rvExplore.adapter = tvSearchAdapter
            viewModel.getTopRatedTvSeries()
        }
    }

    private fun observeTab() {
        binding.tabLayoutExplore.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                currentTab = tab?.position ?: 0
                viewModel.setSelectedTabIndex(currentTab)
                if (searchResult) showContentState()
                if (currentTab == 0) {
                    binding.editTextSearch.setText(savedMovieQuery)
                } else binding.editTextSearch.setText(savedTvQuery)
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabReselected(p0: TabLayout.Tab?) {}
        })
    }

    private fun observeData() {
        viewModel.movieSearchState.observe(viewLifecycleOwner) {
            binding.animationView.gone()
            when (it) {
                is MovieUiState.Loading -> binding.animationView.visible()
                is MovieUiState.Error -> showFancyToast(it.message, FancyToast.ERROR)
                is MovieUiState.Success -> {
                    handleSuccess(it.movieList, movieSearchAdapter)
                }

                else -> Unit
            }
        }
        viewModel.tvSearchState.observe(viewLifecycleOwner) {
            binding.animationView.gone()
            when (it) {
                is ExploreViewModel.TvSeriesUiState.Loading -> binding.animationView.visible()
                is ExploreViewModel.TvSeriesUiState.Error -> showFancyToast(
                    it.message,
                    FancyToast.ERROR
                )

                is ExploreViewModel.TvSeriesUiState.Success -> {
                    handleSuccess(it.tvList, tvSearchAdapter)
                }

                else -> Unit
            }
        }
        viewModel.ratedMovieState.observe(viewLifecycleOwner) {
            binding.animationView.gone()
            when (it) {
                is MovieUiState.Loading -> binding.animationView.visible()
                is MovieUiState.Error -> showFancyToast(it.message, FancyToast.ERROR)
                is MovieUiState.Success -> movieSearchAdapter.submitList(it.movieList)
                else -> Unit
            }
        }
        viewModel.ratedTvSeriesState.observe(viewLifecycleOwner) {
            binding.animationView.gone()
            when (it) {
                is ExploreViewModel.TvSeriesUiState.Loading -> binding.animationView.visible()
                is ExploreViewModel.TvSeriesUiState.Error -> showFancyToast(
                    it.message,
                    FancyToast.ERROR
                )

                is ExploreViewModel.TvSeriesUiState.Success -> tvSearchAdapter.submitList(it.tvList)
                else -> Unit
            }
        }
        viewModel.tvQuery.observe(viewLifecycleOwner) {
            savedTvQuery = it
        }
        viewModel.movieQuery.observe(viewLifecycleOwner) {
            savedMovieQuery = it
        }
        viewModel.selectedTabIndex.observe(viewLifecycleOwner) {
            binding.tabLayoutExplore.getTabAt(it)?.select()
        }
    }

    private fun setupClicks() {
        binding.editTextSearch.addTextChangedListener {
            val query = it.toString().trim()
            if (query.isNotEmpty()) {
                handleSearch(query)
            } else {
                handleEmptySearch()
            }

        }
        movieSearchAdapter.onClick = { id, _ ->
            findNavController().navigate(
                ExploreFragmentDirections.actionExploreFragmentToDetailFragment(
                    true, id
                )
            )
        }
        tvSearchAdapter.onClick = { id, _ ->
            findNavController().navigate(
                ExploreFragmentDirections.actionExploreFragmentToDetailFragment(
                    false, id
                )
            )
        }
    }

    private fun handleSearch(query: String) {
        when (currentTab) {
            0 -> {
                binding.rvExplore.adapter = movieSearchAdapter
                getMovieSearches(query)
                viewModel.saveMovieQuery(query)
            }

            1 -> {
                binding.rvExplore.adapter = tvSearchAdapter
                getTvSearches(query)
                viewModel.saveTvQuery(query)
            }
        }
    }

    private fun handleEmptySearch() {
        when (currentTab) {
            0 -> {
                binding.rvExplore.adapter = movieSearchAdapter
                viewModel.getTopRatedMovies()
                viewModel.saveMovieQuery("")
            }

            else -> {
                binding.rvExplore.adapter = tvSearchAdapter
                viewModel.getTopRatedTvSeries()
                viewModel.saveTvQuery("")
            }
        }
    }

    private fun getMovieSearches(query: String) {
        viewModel.searchMovies(query)
    }

    private fun getTvSearches(query: String) {
        viewModel.searchTvSeries(query)
    }


    private fun <T> handleSuccess(list: List<T>, adapter: ListAdapter<T, *>) {
        searchResult = if (list.isEmpty()) {
            showEmptyState()
            true
        } else {
            showContentState()
            adapter.submitList(list)
            false
        }
    }

    private fun showEmptyState() {
        binding.errorMessage.visible()
        binding.rvExplore.gone()
    }

    private fun showContentState() {
        binding.errorMessage.gone()
        binding.rvExplore.visible()
    }
}