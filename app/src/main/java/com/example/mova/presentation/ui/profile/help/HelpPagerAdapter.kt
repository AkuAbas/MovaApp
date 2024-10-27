package com.example.mova.presentation.ui.profile.help

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HelpPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FaqFragment()
            1 -> ContactFragment()
            else -> throw IllegalArgumentException("invalid position: $position")
        }
    }
}