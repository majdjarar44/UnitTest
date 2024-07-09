package com.example.unittestjunit.newtwork

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow for read but not write

    fun giveContentIfNotHandled(): T? {
        return if (hasBeenHandled) {

            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}