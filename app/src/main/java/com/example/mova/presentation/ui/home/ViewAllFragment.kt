package com.example.mova.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mova.R
import com.example.mova.base.BaseFragment
import com.example.mova.databinding.FragmentViewAllBinding
import com.example.mova.model.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewAllFragment : BaseFragment<FragmentViewAllBinding>(FragmentViewAllBinding::inflate) {

    private val args: ViewAllFragmentArgs by navArgs()
    private val adapter = MovieAdapter(true)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvAllMovies.adapter = adapter
        setAllMovies(args.movies.toList())
        if (args.title == "upcoming") {
            binding.textViewHeader.text = getString(R.string.new_releases)
        }
        setupClicks()
    }

    private fun setAllMovies(list: List<Movie>) {
        adapter.submitList(list)
    }

    private fun setupClicks() {
        adapter.onClick = { id, _ ->
            findNavController().navigate(
                ViewAllFragmentDirections.actionViewAllFragmentToDetailFragment(
                    true, id
                )
            )
        }
        binding.imageViewBackToHome.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}