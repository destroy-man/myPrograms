package ru.korobeynikov.p1hiltunderthehood.other

import android.content.Context
import androidx.fragment.app.Fragment
import ru.korobeynikov.p1hiltunderthehood.di.HiltFragmentComponent

open class HiltOrderFragment : Fragment() {

    private lateinit var fragmentComponent: HiltFragmentComponent

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentComponent = (context as OrderActivity).activityComponent.getFragmentComponent()
        fragmentComponent.injectOrderFragment(this as OrderFragment)
    }
}