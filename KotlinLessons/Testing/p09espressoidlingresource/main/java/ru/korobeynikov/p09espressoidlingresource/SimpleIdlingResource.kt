package ru.korobeynikov.p09espressoidlingresource

import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicBoolean

class SimpleIdlingResource : IdlingResource {

    @Volatile
    private var mCallback: IdlingResource.ResourceCallback? = null
    private val mIsIdleNow = AtomicBoolean(true)

    override fun getName(): String {
        return this::class.simpleName.toString()
    }

    override fun isIdleNow(): Boolean {
        return mIsIdleNow.get()
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        mCallback = callback
    }

    fun setIdleState(isIdleNow: Boolean) {
        mIsIdleNow.set(isIdleNow)
        if (isIdleNow && mCallback != null)
            mCallback?.onTransitionToIdle()
    }
}