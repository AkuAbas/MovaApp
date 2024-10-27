package com.example.mova.presentation.ui.profile.editProfile

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mova.R
import com.example.mova.base.BaseFragment
import com.example.mova.databinding.FragmentEditProfileBinding
import com.example.mova.model.Account
import com.example.mova.presentation.ui.profile.myProfile.ProfileViewModel
import com.example.mova.utils.loadImage
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {

    private val profileViewModel by activityViewModels<ProfileViewModel>()
    private val editProfileViewModel by viewModels<EditProfileViewModel>()
    private var selectedCountry: String? = null
    private var selectedGender: String? = null
    private var profileImageUrl: String? = null
    private var profileEmail: String? = ""
    private var initialAccount: Account? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        editProfileViewModel.getEmailFromPreferences()
        profileViewModel.getDataFromFireStore()
        setUpClicks()
        selectProfileImage()
    }

    private fun observeData() {
        profileViewModel.data.observe(viewLifecycleOwner) {
            binding.EditTextEditFullName.setText(it.fullName)
            binding.EditTextEditNickname.setText(it.nickName)
            it.image?.let { imageUrl ->
                binding.imageViewProfile.loadImage(imageUrl)
                profileImageUrl = imageUrl
            }

            selectedCountry = it.country
            selectedGender = it.gender

            selectedCountry?.let { country ->
                setUpSpinner(
                    binding.spinnerCountry, R.array.countries_array,
                    country
                )
            }
            selectedGender?.let { gender ->
                setUpSpinner(
                    binding.spinnerGender, R.array.gender_array,
                    gender
                )
            }

            initialAccount =
                Account(it.fullName, it.nickName, it.email, it.country, it.gender, it.image)
        }
        editProfileViewModel.email.observe(viewLifecycleOwner) {
            profileEmail = it
        }
    }

    private fun setUpSpinner(spinner: Spinner, arrayResourceId: Int, selectedItem: String) {
        val items = resources.getStringArray(arrayResourceId)
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                items[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        setSpinnerPosition(adapter, spinner, selectedItem)
    }

    private fun setSpinnerPosition(
        adapter: ArrayAdapter<String>,
        spinner: Spinner,
        selectedItem: String
    ) {
        val position = adapter.getPosition(selectedItem)
        spinner.setSelection(position)
    }

    private fun selectProfileImage() {
        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {
                binding.imageViewProfile.setImageURI(it)
                profileImageUrl = it.toString()
            }
        }

        binding.buttonEdit.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }


    private fun setUpClicks() {
        binding.imageViewArrowEditToHome.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonUpdate.setOnClickListener {
            val fullName = binding.EditTextEditFullName.text.toString().trim()
            val nickName = binding.EditTextEditNickname.text.toString().trim()
            val country = binding.spinnerCountry.selectedItem.toString()
            val gender = binding.spinnerGender.selectedItem.toString()

            if (fullName.isEmpty() || nickName.isEmpty()) {
                showFancyToast("Please fill all required fields.", FancyToast.WARNING)
                return@setOnClickListener
            }

            val updatedAccount =
                Account(fullName, nickName, profileEmail, country, gender, profileImageUrl)
            if (updatedAccount != initialAccount) {
                editProfileViewModel.updateFireStore(updatedAccount)
                showFancyToast("Updated successfully!", FancyToast.SUCCESS)
            }
        }
    }
}