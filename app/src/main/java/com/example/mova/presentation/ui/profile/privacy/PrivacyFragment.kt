package com.example.mova.presentation.ui.profile.privacy

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.mova.databinding.FragmentPrivacyBinding

class PrivacyFragment : BaseFragment<FragmentPrivacyBinding>(FragmentPrivacyBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageViewPrivacyToHome.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}