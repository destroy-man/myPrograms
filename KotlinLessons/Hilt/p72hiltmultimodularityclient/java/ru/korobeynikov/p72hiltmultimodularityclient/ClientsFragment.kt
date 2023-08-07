package ru.korobeynikov.p72hiltmultimodularityclient

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import ru.korobeynikov.p72hiltmultimodularityclient.databinding.FragmentClientsBinding
import ru.korobeynikov.p73hiltmultimodularitydata.Database
import javax.inject.Inject

@AndroidEntryPoint
class ClientsFragment : Fragment() {

    @Inject
    lateinit var database: Database

    private val logTag = "myLogs"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        Log.d(logTag, "database = $database")
        val binding = DataBindingUtil
            .inflate<FragmentClientsBinding>(inflater, R.layout.fragment_clients, container, false)
        return binding.root
    }
}