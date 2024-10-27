package com.example.mova.presentation.ui.profile.help

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mova.databinding.ContactItemBinding

class ContactAdapter :
    ListAdapter<Contact, ContactAdapter.ContactViewHolder>(ContactDiffCallback()) {

    inner class ContactViewHolder(val contactItemBinding: ContactItemBinding) :
        RecyclerView.ViewHolder(contactItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val data = getItem(position)
        holder.contactItemBinding.contact = data
        holder.contactItemBinding.imageViewContactIcon.setImageResource(data.icon)
    }

    class ContactDiffCallback : ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }

    }
}