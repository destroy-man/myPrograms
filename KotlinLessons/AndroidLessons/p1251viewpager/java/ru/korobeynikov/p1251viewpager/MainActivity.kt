package ru.korobeynikov.p1251viewpager

import android.os.Bundle
import android.support.v4.app.*
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity:FragmentActivity() {

    companion object{
        val TAG="myLogs"
        val PAGE_COUNT=10
    }

    lateinit var pagerAdapter:PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pagerAdapter=MyFragmentPagerAdapter(supportFragmentManager)
        pager.adapter=pagerAdapter
        pager.setOnPageChangeListener(object:ViewPager.OnPageChangeListener{
            override fun onPageSelected(position: Int) {
                Log.d(TAG,"onPageSelected, position = $position")
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

            override fun onPageScrollStateChanged(p0: Int) {}
        })
    }

    private class MyFragmentPagerAdapter(fm:FragmentManager):FragmentStatePagerAdapter(fm){

        override fun getItem(position: Int): Fragment {
            return PageFragment.newInstance(position)
        }

        override fun getCount(): Int {
            return PAGE_COUNT
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return "Title $position"
        }
    }
}