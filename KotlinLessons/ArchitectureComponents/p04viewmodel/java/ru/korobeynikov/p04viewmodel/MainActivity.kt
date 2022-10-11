package ru.korobeynikov.p04viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    class MasterFragment : Fragment() {

        private lateinit var model: SharedViewModel

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            model = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
            val v = inflater.inflate(R.layout.master_fragment, null)
            val itemSelector = v.findViewById<Button>(R.id.itemSelector)
            itemSelector.setOnClickListener {
                model.select(Item(1L, "Ivan"))
            }
            return v
        }
    }

    class DetailFragment : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val model = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
            val v = inflater.inflate(R.layout.detail_fragment, null)
            val textView = v.findViewById<TextView>(R.id.textView)
            model.getSelected().observe(requireActivity()) { item ->
                textView.text = "${item.id} - ${item.name}"
            }
            return v
        }
    }
}