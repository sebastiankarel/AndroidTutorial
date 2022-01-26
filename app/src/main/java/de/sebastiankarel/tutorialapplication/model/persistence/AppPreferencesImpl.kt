package de.sebastiankarel.tutorialapplication.model.persistence

import android.content.Context
import androidx.lifecycle.LiveData
import de.sebastiankarel.tutorialapplication.util.SharedPreferenceStringLiveData

class AppPreferencesImpl(private val context: Context) : AppPreferences {

    private val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val prefsLiveData = SharedPreferenceStringLiveData(prefs, TOKEN_KEY, null)

    override fun getToken(): String? = prefs.getString(TOKEN_KEY, null)

    override fun setToken(token: String) = prefs.edit().putString(TOKEN_KEY, token).apply()

    override fun removeToken() = prefs.edit().remove(TOKEN_KEY).apply()

    override fun getTokenLiveData(): LiveData<String?> {
        return prefsLiveData
    }

    override fun getPageNumber(): Int = prefs.getInt(PAGE_KEY, 1)

    override fun incrementPageNumber() {
        val current = prefs.getInt(PAGE_KEY, 1)
        prefs.edit().putInt(PAGE_KEY, current + 1).apply()
    }

    override fun resetPageNumber() = prefs.edit().putInt(PAGE_KEY, 1).apply()

    companion object {
        private const val PREF_NAME = "app_preferences"
        private const val TOKEN_KEY = "token"
        private const val PAGE_KEY = "page"
    }
}