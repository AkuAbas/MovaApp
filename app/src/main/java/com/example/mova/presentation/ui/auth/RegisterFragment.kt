package com.example.mova.presentation.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.mova.databinding.FragmentRegisterBinding
import com.example.mova.utils.gone
import com.example.mova.utils.visible
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel by viewModels<RegisterViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupClicks()
    }

    private fun setupClicks() {
        binding.buttonSignUp.setOnClickListener {
            signUp()
        }
        binding.buttonSignIn.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
        binding.imageViewArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun signUp() {
        val email = binding.EditTextEmail.text.toString().trim()
        val password = binding.EditTextPassword.text.toString().trim()
        viewModel.registerUser(email, password)
    }

    private fun observeData() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            binding.animationView.gone()

            when (it) {
                is RegisterViewModel.RegisterUiState.Loading -> binding.animationView.visible()
                is RegisterViewModel.RegisterUiState.EmptyFields -> showFancyToast(
                    "Please fill in all required fields.",
                    FancyToast.WARNING
                )

                is RegisterViewModel.RegisterUiState.Error -> showFancyToast(
                    it.message,
                    FancyToast.ERROR
                )

                is RegisterViewModel.RegisterUiState.Success -> {
                    if (binding.checkBoxRemember.isChecked) {
                        viewModel.putSharedPref()
                    }
                    showFancyToast(
                        "Your account has been successfully created!",
                        FancyToast.SUCCESS
                    )
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToFillProfileFragment())
                }

            }
        }
    }


}