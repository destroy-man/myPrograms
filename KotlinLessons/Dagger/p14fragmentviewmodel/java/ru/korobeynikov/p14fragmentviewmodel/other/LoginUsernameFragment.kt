package ru.korobeynikov.p14fragmentviewmodel.other

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.korobeynikov.p14fragmentviewmodel.R
import ru.korobeynikov.p14fragmentviewmodel.databinding.FragmentLoginUsernameBinding
import ru.korobeynikov.p14fragmentviewmodel.di.LoginComponent

class LoginUsernameFragment : Fragment() {

    lateinit var loginComponent: LoginComponent

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginComponent = (activity as LoginActivity).loginComponent
        Log.d(MainActivity.LOG_TAG, "${loginComponent.hashCode()}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil.inflate<FragmentLoginUsernameBinding>(inflater,
            R.layout.fragment_login_username, container, false)
        return binding.root
    }
}