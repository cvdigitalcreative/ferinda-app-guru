package com.digitalcreative.appguru.presentation.ui.assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: FragmentActivity, private val bundle: Bundle) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                AssignmentFragment().apply {
                    arguments = bundle
                }
            }
            1 -> {
                SubmittedAssignmentFragment().apply {
                    arguments = bundle
                }
            }
            else -> {
                AssignmentFragment().apply {
                    arguments = bundle
                }
            }
        }
    }
}