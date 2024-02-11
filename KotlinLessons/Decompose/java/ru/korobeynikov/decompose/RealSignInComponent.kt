package ru.korobeynikov.decompose

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class RealSignInComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext, SignInComponent {

    private val coroutineScope = componentCoroutineScope()

    override val login = MutableStateFlow("")

    override val password = MutableStateFlow("")

    override val inProgress = MutableStateFlow(false)

    override fun onLoginChanged(login: String) {
        this.login.value = login
    }

    override fun onPasswordChanged(password: String) {
        this.password.value = password
    }

    override fun onSignInClick() {
        coroutineScope.launch {
            inProgress.value = true
            TimeUnit.SECONDS.sleep(3)
            Log.d(
                "myLogs",
                "Login: ${login.value}. Password: ${password.value}. Авторизация прошла успешно!"
            )
            inProgress.value = false
        }
    }

    private fun ComponentContext.componentCoroutineScope(): CoroutineScope {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        if (lifecycle.state != Lifecycle.State.DESTROYED) {
            lifecycle.doOnDestroy {
                scope.cancel()
            }
        } else {
            scope.cancel()
        }
        return scope
    }
}