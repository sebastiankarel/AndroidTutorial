package de.sebastiankarel.tutorialapplication.util

class Event<T>(private val data: T) {

    private var hasBeenConsumed: Boolean = false

    fun getOrNull(): T? {
        return if (!hasBeenConsumed) {
            hasBeenConsumed = true
            data
        } else null
    }
}