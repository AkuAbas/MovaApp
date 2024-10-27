package com.example.mova.presentation.ui.profile.help

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mova.databinding.FaqItemBinding

class FAQAdapter : ListAdapter<FAQ, FAQAdapter.FaqViewHolder>(FaqDiffCallback()) {

    lateinit var onClick: (FaqItemBinding) -> Unit

    inner class FaqViewHolder(val faqItemBinding: FaqItemBinding) :
        RecyclerView.ViewHolder(faqItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val view = FaqItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FaqViewHolder(view)
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        val data = getItem(position)
        holder.faqItemBinding.faq = data
        holder.faqItemBinding.imageViewDropdown.setOnClickListener {
            onClick(holder.faqItemBinding)
        }
    }

    class FaqDiffCallback : ItemCallback<FAQ>() {
        override fun areItemsTheSame(oldItem: FAQ, newItem: FAQ): Boolean {
            return oldItem.question == newItem.question
        }

        override fun areContentsTheSame(oldItem: FAQ, newItem: FAQ): Boolean {
            return oldItem == newItem
        }
    }
}