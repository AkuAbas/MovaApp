package com.example.mova.presentation.ui.profile.help

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.mova.databinding.FragmentHelpBinding
import com.google.android.material.tabs.TabLayoutMediator

class HelpFragment : BaseFragment<FragmentHelpBinding>(FragmentHelpBinding::inflate) {

    private val viewPagerAdapter by lazy { HelpPagerAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.helpViewPager.adapter = viewPagerAdapter
        setTabMediator()
        setUpClicks()
    }

    private fun setTabMediator() {
        TabLayoutMediator(binding.helpTabLayout, binding.helpViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "FAQ"
                1 -> tab.text = "Contact Us"
            }
        }.attach()
    }

    private fun setUpClicks() {
        binding.imageViewHelpToHome.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}