package com.example.mova.presentation.ui.profile.help

import android.os.Bundle
import android.view.View
import com.example.mova.R
import com.example.mova.base.BaseFragment
import com.example.mova.databinding.FragmentFaqBinding
import com.example.mova.utils.gone
import com.example.mova.utils.visible

class FaqFragment : BaseFragment<FragmentFaqBinding>(FragmentFaqBinding::inflate) {

    private val faqAdapter = FAQAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFAQ.adapter = faqAdapter
        getFaqList()
        setUpClicks()

    }

    private fun setUpClicks() {
        faqAdapter.onClick = {
            it.imageViewDropdown.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    it.textViewFaqAnswer.visible()
                } else {
                    it.textViewFaqAnswer.gone()
                }
            }
        }
    }

    private fun getFaqList() {
        faqAdapter.submitList(
            listOf(
                FAQ("What is Mova?", getString(R.string.what_is_mova)),
                FAQ("How do I add content to the list?", getString(R.string.how_to_add_content)),
                FAQ(
                    "How do I delete content from the list?",
                    getString(R.string.how_to_delete_content)
                ),
                FAQ(
                    "How do I change my user information?",
                    getString(R.string.how_to_change_user_info)
                ),
                FAQ("How do I subscribe to premium?", getString(R.string.lorem)),
                FAQ("How can I contact customer support?", getString(R.string.lorem)),
                FAQ("What payment methods are accepted?", getString(R.string.lorem)),
            )
        )
    }

}