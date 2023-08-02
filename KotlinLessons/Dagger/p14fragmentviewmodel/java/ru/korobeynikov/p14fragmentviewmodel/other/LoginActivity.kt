package ru.korobeynikov.p14fragmentviewmodel.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p14fragmentviewmodel.di.LoginComponent
import ru.korobeynikov.p14fragmentviewmodel.R
import ru.korobeynikov.p14fragmentviewmodel.databinding.ActivityLoginBinding
import ru.korobeynikov.p14fragmentviewmodel.di.DaggerLoginComponent
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var loginViewModel: LoginViewModel

    lateinit var loginComponent: LoginComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        loginComponent = DaggerLoginComponent.create()
        loginComponent.inject(this)
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
    }
}