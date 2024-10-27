package com.example.mova.presentation.ui.profile.myProfile

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.mova.base.BaseFragment
import com.example.mova.databinding.CustomLogoutDialogBinding
import com.example.mova.databinding.FragmentProfileBinding
import com.example.mova.utils.loadImage
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel by viewModels<ProfileViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAppTheme()
        observeData()
        viewModel.getDataFromFireStore()
        setupClicks()
    }

    private fun observeData() {
        viewModel.data.observe(viewLifecycleOwner) {
            binding.textViewFullName.text = it.fullName
            binding.textViewUserEmail.text = it.email
            it.image?.let { imageUrl -> binding.imageViewProfile.loadImage(imageUrl) }
        }
        viewModel.isDarkMode.observe(viewLifecycleOwner) { isDarkMode ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }
    }

    private fun setupClicks() {
        setUpClick(
            binding.constraintLayoutEditProfile,
            ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
        )
        setUpClick(
            binding.constraintLayoutNotification,
            ProfileFragmentDirections.actionProfileFragmentToProfileNotificationFragment()
        )
        setUpClick(
            binding.constraintLayoutHelp,
            ProfileFragmentDirections.actionProfileFragmentToHelpFragment()
        )
        setUpClick(
            binding.constraintLayoutPrivacy,
            ProfileFragmentDirections.actionProfileFragmentToPrivacyFragment()
        )
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                viewModel.setAppTheme(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                viewModel.setAppTheme(false)
            }
        }
        binding.buttonLogOut.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        val alertBinding = CustomLogoutDialogBinding.inflate(layoutInflater)
        alertDialog.setView(alertBinding.root)
        alertDialog.show()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        alertBinding.buttonYesLogout.setOnClickListener {
            viewModel.putSharedPreferences()
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToAuthFragment())
            showFancyToast("Logged out successfully", FancyToast.SUCCESS)
            alertDialog.dismiss()
        }
        alertBinding.buttonCancel.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    private fun setUpClick(view: View, action: NavDirections) {
        view.setOnClickListener {
            findNavController().navigate(action)
        }
    }


}