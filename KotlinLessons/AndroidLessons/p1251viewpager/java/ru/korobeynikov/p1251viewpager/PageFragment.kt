package ru.korobeynikov.p1251viewpager

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1251viewpager.databinding.FragmentPageBinding
import java.util.*

class PageFragment : Fragment() {

    companion object {

        const val ARGUMENT_PAGE_NUMBER = "arg_page_number"
        const val SAVE_PAGE_NUMBER = "save_page_number"

        fun newInstance(page: Int): PageFragment {
            val pageFragment = PageFragment()
            val arguments = Bundle()
            arguments.putInt(ARGUMENT_PAGE_NUMBER, page)
            pageFragment.arguments = arguments
            return pageFragment
        }
    }

    private var pageNumber = 0
    private var backColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = arguments?.getInt(ARGUMENT_PAGE_NUMBER) ?: 0
        Log.d(MainActivity.TAG, "onCreate: $pageNumber")
        val rnd = Random()
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        var savedPageNumber = -1
        if (savedInstanceState != null)
            savedPageNumber = savedInstanceState.getInt(SAVE_PAGE_NUMBER)
        Log.d(MainActivity.TAG, "savedPageNumber = $savedPageNumber")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding =
            DataBindingUtil.inflate<FragmentPageBinding>(inflater, R.layout.fragment_page, container, false)
        with(binding.tvPage) {
            text = getString(R.string.page_text, pageNumber)
            setBackgroundColor(backColor)
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SAVE_PAGE_NUMBER, pageNumber)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(MainActivity.TAG, "onDestroy: $pageNumber")
    }
}