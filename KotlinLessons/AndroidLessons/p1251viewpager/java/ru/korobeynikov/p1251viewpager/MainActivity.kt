package ru.korobeynikov.p1251viewpager

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import ru.korobeynikov.p1251viewpager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "myLogs"
        const val PAGE_COUNT = 10
    }

    private lateinit var pager: ViewPager2
    private lateinit var fragmentStateAdapter: FragmentStateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        pager = binding.pager
        fragmentStateAdapter = MyFragmentStateAdapter(this)
        pager.adapter = fragmentStateAdapter
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d(TAG, "onPageSelected, position = $position")
            }
        })
        TabLayoutMediator(binding.tabLayout, pager) { tab, position ->
            tab.text = "Title $position"
        }.attach()
    }

    private class MyFragmentStateAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        override fun createFragment(position: Int) = PageFragment.newInstance(position)

        override fun getItemCount() = PAGE_COUNT
    }
}