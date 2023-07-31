package ru.korobeynikov.p12multiscope.other

import android.app.Activity
import ru.korobeynikov.p12multiscope.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class UiHelper @Inject constructor(activity: Activity)