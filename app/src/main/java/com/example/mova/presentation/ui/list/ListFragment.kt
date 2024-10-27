package com.example.mova.presentation.ui.list

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.mova.base.BaseFragment
import com.example.mova.databinding.CustomDeleteDialogBinding
import com.example.mova.databinding.FragmentListBinding
import com.example.mova.local.ListedContent
import com.example.mova.local.Movie
import com.example.mova.presentation.ui.detail.DetailViewModel
import com.example.mova.utils.gone
import com.example.mova.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>(FragmentListBinding::inflate) {

    private val listViewModel by viewModels<ListViewModel>()
    private val detailViewModel by activityViewModels<DetailViewModel>()
    private val listAdapter = MyListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMyList.adapter = listAdapter
        observeData()
        listViewModel.getAllContent()
        setupClicks()
        searchMyList()
    }

    private fun observeData() {
        listViewModel.movieList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.listErrorMessage.gone()
                binding.rvMyList.visible()
                binding.textViewListNotFound.gone()
                listAdapter.submitList(it)
            } else {
                binding.listErrorMessage.visible()
                binding.rvMyList.gone()
                binding.textViewListNotFound.gone()
            }
        }
        listViewModel.searchList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                listAdapter.submitList(it)
                binding.rvMyList.visible()
                binding.textViewListNotFound.gone()
            } else {
                binding.rvMyList.gone()
                binding.listErrorMessage.gone()
                binding.textViewListNotFound.visible()
            }

        }
    }

    private fun showAlertDialog(movie: Movie) {
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        val alertBinding = CustomDeleteDialogBinding.inflate(layoutInflater)
        alertDialog.setView(alertBinding.root)
        alertDialog.show()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        alertBinding.buttonYes.setOnClickListener {
            listViewModel.deleteContent(movie)
            detailViewModel.deleteListedContentId(ListedContent(movie.contentId, movie.id))
            alertDialog.dismiss()
        }
        alertBinding.buttonNo.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    private fun searchMyList() {
        binding.editTextSearch.addTextChangedListener {
            val query = it.toString()
            if (query.isNotEmpty()) {
                listViewModel.searchMyList(query)
            } else {
                listViewModel.getAllContent()
            }
        }
    }

    private fun setupClicks() {
        listAdapter.onClick = {
            showAlertDialog(it)
        }

        binding.imageViewListSearch.setOnClickListener {
            binding.textInputLayout.visible()
            binding.textViewMyList.gone()
            binding.imageViewListSearch.gone()
        }
    }


}