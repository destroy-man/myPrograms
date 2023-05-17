package ru.korobeynikov.p1151multiplescreen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.korobeynikov.p1151multiplescreen.databinding.FragmentTitlesBinding

class TitlesFragment : Fragment() {

    interface OnItemClickListener {
        fun itemClick(position: Int)
    }

    lateinit var listener: OnItemClickListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil
            .inflate<FragmentTitlesBinding>(inflater, R.layout.fragment_titles, container, false)
        val layoutManager = LinearLayoutManager(context)
        with(binding.rvList) {
            this.layoutManager = layoutManager
            val dividerItemDecoration = DividerItemDecoration(this.context, layoutManager.orientation)
            addItemDecoration(dividerItemDecoration)
            adapter = ListAdapter(resources.getStringArray(R.array.headers))
            addOnItemTouchListener(RecyclerTouchListener(context,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {
                        listener.itemClick(position)
                    }
                }))
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = activity as OnItemClickListener
    }
}