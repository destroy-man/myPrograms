package ru.korobeynikov.p3hiltmodulesscope

import android.app.Application
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
class NetworkUtils(app: Application)