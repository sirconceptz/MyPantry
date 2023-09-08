package com.hermanowicz.pantry.utils

import androidx.test.espresso.IdlingResource

class AnimationIdlingResource(private var isIdle: Boolean = false) : IdlingResource {
    private var callback: IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        return "AnimationIdlingResource"
    }

    override fun isIdleNow(): Boolean {
        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }

    fun setIdle(idle: Boolean) {
        isIdle = idle
        callback?.onTransitionToIdle()
    }
}
