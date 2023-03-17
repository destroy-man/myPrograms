package ru.korobeynikov.p0761tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        val fragment = TabFragment()
        fragment.arguments = Bundle().apply {
            when (position) {
                0 -> putString(Constants.ARG_OBJECT, "Это первая вкладка")
                1 -> putString(Constants.ARG_OBJECT, "Это вторая вкладка")
                2 -> putString(Constants.ARG_OBJECT, "Это третья вкладка")
            }
        }
        return fragment
    }
}