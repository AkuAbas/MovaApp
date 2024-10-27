package com.example.mova.presentation.ui.profile.fillProfile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mova.R
import com.example.mova.base.BaseFragment
import com.example.mova.databinding.FragmentFillProfileBinding
import com.example.mova.model.Account
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FillProfileFragment :
    BaseFragment<FragmentFillProfileBinding>(FragmentFillProfileBinding::inflate) {

    private val viewModel by viewModels<FillProfileViewModel>()
    private var selectedCountry: String = ""
    private var selectedGender: String = ""
    private var imageUri: Uri? = null
    private val storageRef = Firebase.storage.reference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        viewModel.getEmailFromPreferences()
        setAllSpinners()
        setupClicks()
        selectProfileImage()
    }

    private fun observeData() {
        viewModel.email.observe(viewLifecycleOwner) {
            binding.EditTextFillEmail.setText(it)
        }
    }

    private fun setAllSpinners() {
        setupSpinner(binding.spinnerCountry, R.array.countries_array)
        setupSpinner(binding.spinnerGender, R.array.gender_array)
    }


    private fun saveUserData() {
        val fullName = binding.EditTextFullName.text.toString().trim()
        val nickName = binding.EditTextNickname.text.toString().trim()
        val email = binding.EditTextFillEmail.text.toString().trim()

        if (fullName.isEmpty() || nickName.isEmpty()) {
            showFancyToast("Please fill all required fields.", FancyToast.WARNING)
            return
        }

        val account =
            Account(fullName, nickName, email, selectedCountry, selectedGender, imageUri.toString())
        viewModel.saveDataToFireStore(account)
        showFancyToast("Profile has been created successfully!", FancyToast.SUCCESS)
        navigateToHome()
    }

    private fun saveNullData() {
        val email = binding.EditTextFillEmail.text.toString().trim()
        val emptyAccount =
            Account("", "", email, selectedCountry, selectedGender, null)
        viewModel.saveDataToFireStore(emptyAccount)
        navigateToHome()
    }


    private fun setupSpinner(spinner: Spinner, arrayResourceId: Int) {
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
                if (spinner.id == R.id.spinnerCountry) {
                    selectedCountry = items[position]
                } else selectedGender = items[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupClicks() {
        binding.buttonSkip.setOnClickListener {
            saveNullData()
        }

        binding.buttonContinue.setOnClickListener {
            saveUserData()
        }
    }


    private fun selectProfileImage() {
        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            it?.let { uri ->
                binding.imageViewProfile.setImageURI(uri)
                saveImageToStorage(uri)
            }
        }

        binding.buttonEdit.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun saveImageToStorage(uri: Uri) {
        val imageRef = storageRef.child("images")

        imageRef.putFile(uri).addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                imageUri = uri
            }.addOnFailureListener { Log.e("Firebase", "Failed in downloading") }
        }.addOnFailureListener { Log.e("Firebase", "Image upload fail") }
    }

    private fun navigateToHome() {
        findNavController().navigate(FillProfileFragmentDirections.actionFillProfileFragmentToHomeFragment())
    }


}