package de.sebastiankarel.tutorialapplication.util

import androidx.lifecycle.Observer

class EventObserver<T>(private val onChangedCallback: (data: T) -> Unit) : Observer<Event<T>> {

    override fun onChanged(t: Event<T>) {
        val data = t.getOrNull()
        if (data != null) onChangedCallback(data)
    }
}