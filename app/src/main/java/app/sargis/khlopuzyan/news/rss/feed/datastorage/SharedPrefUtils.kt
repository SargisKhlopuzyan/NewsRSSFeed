package app.sargis.khlopuzyan.news.rss.feed.datastorage

import android.content.Context
import android.content.SharedPreferences
import app.sargis.khlopuzyan.news.rss.feed.constant.SharedPrefConst
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefUtils @Inject constructor(var context: Context) {

    private fun getPreferences(): SharedPreferences {
        return context.getSharedPreferences(SharedPrefConst.SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun storeStringInPreference(key: String, value: String) {
        val sharedPreferences = getPreferences()
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun loadStringFromPreference(key: String): String ? {
        val sharedPreferences = getPreferences()
        return sharedPreferences.getString(key, null)
    }


    fun storeBooleanInPreference(key: String, value: Boolean) {
        val sharedPreferences = getPreferences()
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun loadBooleanFromPreference(key: String): Boolean {
        val sharedPreferences = getPreferences()
        return sharedPreferences.getBoolean(key, false)
    }
}