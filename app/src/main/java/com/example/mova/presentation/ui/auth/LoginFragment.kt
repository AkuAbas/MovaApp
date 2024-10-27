package com.example.mova.presentation.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mova.databinding.FragmentLoginBinding
import com.example.mova.base.BaseFragment
import com.example.mova.utils.gone
import com.example.mova.utils.visible
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupClicks()
    }

    private fun setupClicks() {
        binding.buttonSignIn.setOnClickListener {
            login()
        }
        binding.buttonSignUp.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
        binding.imageViewArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun login() {
        val email = binding.EditTextEmail.text.toString().trim()
        val password = binding.EditTextPassword.text.toString().trim()
        viewModel.loginUser(email, password)
    }

    private fun observeData() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            binding.animationView.gone()

            when (it) {
                is LoginViewModel.LoginUiState.EmptyFields -> showFancyToast(
                    "Please fill in all required fields.",
                    FancyToast.WARNING
                )

                is LoginViewModel.LoginUiState.Loading -> binding.animationView.visible()
                is LoginViewModel.LoginUiState.Error -> showFancyToast(it.message, FancyToast.ERROR)

                is LoginViewModel.LoginUiState.Success -> {
                    if (binding.checkBoxRemember.isChecked) {
                        viewModel.putSharedPref()
                    }
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                    showFancyToast("Successfully logged in.", FancyToast.SUCCESS)
                }
            }
        }
    }

}