package ru.korobeynikov.p1091listfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.korobeynikov.p1091listfragment.databinding.FragmentMainListBinding

class MainList : Fragment() {

    private val data = arrayOf("one", "two", "three", "four")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil
            .inflate<FragmentMainListBinding>(inflater, R.layout.fragment_main_list, container, false)
        val rvList = binding.rvList
        if (data.isNotEmpty()) {
            val layoutManager = LinearLayoutManager(context)
            with(rvList) {
                this.layoutManager = layoutManager
                val dividerItemDecoration = DividerItemDecoration(this.context, layoutManager.orientation)
                addItemDecoration(dividerItemDecoration)
                adapter = ListAdapter(data)
                addOnItemTouchListener(RecyclerTouchListener(context,
                    object : RecyclerTouchListener.ClickListener {
                        override fun onClick(view: View, position: Int) {
                            Toast.makeText(context, "position = $position", Toast.LENGTH_SHORT).show()
                        }
                    }))
            }
        } else {
            rvList.visibility = View.GONE
            binding.empty.visibility = View.VISIBLE
        }
        return binding.root
    }
}