package de.sebastiankarel.tutorialapplication

import android.content.Context

class AppPreferencesImpl(context: Context) : AppPreferences {

    private val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override fun setEnteredText(text: String) {
        prefs.edit().putString(KEY_ENTERED_TEXT, text).apply()
    }

    override fun getEnteredText(): String = prefs.getString(KEY_ENTERED_TEXT, "") ?: ""

    companion object {
        private const val PREF_NAME = "tutorial_prefs"
        private const val KEY_ENTERED_TEXT = "entered_text"
    }
}