package ru.korobeynikov.p1151multiplescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.korobeynikov.p1151multiplescreen.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance(pos: Int): DetailsFragment {
            val details = DetailsFragment()
            val args = Bundle()
            args.putInt("position", pos)
            details.arguments = args
            return details
        }
    }

    fun getPosition() = arguments?.getInt("position", 0) ?: 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil
            .inflate<FragmentDetailsBinding>(inflater, R.layout.fragment_details, container, false)
        binding.tvText.text = resources.getStringArray(R.array.content)[getPosition()]
        return binding.root
    }
}