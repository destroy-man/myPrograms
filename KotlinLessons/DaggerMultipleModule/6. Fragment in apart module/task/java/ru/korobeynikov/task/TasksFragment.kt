package ru.korobeynikov.task

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.korobeynikov.data.Database
import ru.korobeynikov.task.databinding.FragmentTasksBinding
import javax.inject.Inject

class TasksFragment : Fragment() {

    @Inject
    lateinit var database: Database

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as TaskComponentProvider).getTaskComponent()
            .injectTasksFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentTasksBinding.inflate(layoutInflater)
        return binding.root
    }
}