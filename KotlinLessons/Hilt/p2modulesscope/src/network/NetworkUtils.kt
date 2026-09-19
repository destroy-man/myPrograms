package ru.korobeynikov.p2modulesscope.network

import android.app.Activity
import android.app.Application
import dagger.hilt.android.scopes.ActivityScoped
import ru.korobeynikov.p2modulesscope.Utils

@ActivityScoped
class NetworkUtils(private val app: Application, private val activity: Activity) : Utils