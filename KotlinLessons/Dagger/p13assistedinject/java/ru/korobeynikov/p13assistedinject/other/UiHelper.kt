package ru.korobeynikov.p13assistedinject.other

import android.app.Activity
import ru.korobeynikov.p13assistedinject.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class UiHelper @Inject constructor(activity: Activity)