package ru.korobeynikov.p1hiltunderthehood.other

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.korobeynikov.p1hiltunderthehood.di.HiltActivityComponent

open class HiltUserActivity() : AppCompatActivity() {

    private lateinit var activityComponent: HiltActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = (application as App).appComponent.getActivityComponent()
        activityComponent.injectUserActivity(this as UserActivity)
    }
}