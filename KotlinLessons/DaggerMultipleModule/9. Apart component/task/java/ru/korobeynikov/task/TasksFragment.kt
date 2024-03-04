package ru.korobeynikov.task

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.korobeynikov.task.databinding.FragmentTasksBinding
import ru.korobeynikov.task.di.DaggerTaskComponent
import ru.korobeynikov.task.di.MyTaskComponent
import ru.korobeynikov.task.di.TaskComponentDependenciesProvider
import javax.inject.Inject

class TasksFragment : Fragment() {

    @Inject
    lateinit var taskRepository: TaskRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val taskComponentDependencies =
            (context.applicationContext as TaskComponentDependenciesProvider).getTaskComponentDependencies()
        //val taskComponent =
        //    DaggerTaskComponent.builder().taskComponentDependencies(taskComponentDependencies)
        //        .build()
        val taskComponent = MyTaskComponent(taskComponentDependencies)
        taskComponent.injectTasksFragment(this)
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