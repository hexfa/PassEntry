package com.example.passentry.utils

import androidx.test.espresso.IdlingResource

import java.util.concurrent.atomic.AtomicInteger

class SimpleCountingIdlingResource(private val resourceName: String) : IdlingResource {

    private val counter = AtomicInteger(0)

    // written from main thread, read from any thread.
    @Volatile
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName() = resourceName

    override fun isIdleNow() = counter.get() == 0

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
        this.resourceCallback = resourceCallback
    }

    fun increment() {
        counter.getAndIncrement()
    }

    fun decrement() {
        val counterVal = counter.decrementAndGet()
        if (counterVal == 0) {
            // we've gone from non-zero to zero. That means we're idle now! Tell espresso.
            resourceCallback?.onTransitionToIdle()
        } else if (counterVal < 0) {
            throw IllegalStateException("Counter has been corrupted!")
        }
    }
}
