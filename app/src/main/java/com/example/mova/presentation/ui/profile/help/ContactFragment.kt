package com.example.mova.presentation.ui.profile.help

import android.os.Bundle
import android.view.View
import com.example.mova.R
import com.example.mova.base.BaseFragment
import com.example.mova.databinding.FragmentContactBinding

class ContactFragment : BaseFragment<FragmentContactBinding>(FragmentContactBinding::inflate) {

    private val contactAdapter = ContactAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvContactUs.adapter = contactAdapter
        setUpContacts()
    }

    private fun setUpContacts() {
        contactAdapter.submitList(
            listOf(
                Contact("Customer Service", R.drawable.headphone),
                Contact("WhatsApp", R.drawable.whatsapp),
                Contact("Website", R.drawable.web),
                Contact("Facebook", R.drawable.facebook),
                Contact("Twitter", R.drawable.twitter),
                Contact("Instagram", R.drawable.instagram)
            )
        )
    }

}