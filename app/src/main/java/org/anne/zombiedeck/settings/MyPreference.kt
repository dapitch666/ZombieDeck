package org.anne.zombiedeck.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class MyPreference @Inject constructor(@ApplicationContext context : Context?){
    private val prefs: SharedPreferences? = context?.let { PreferenceManager.getDefaultSharedPreferences(it) }

    open fun getBoolean(key: String, defValue: Boolean = true): Boolean {
        return prefs?.getBoolean(key, defValue) ?: defValue
    }

    open fun setBoolean(key: String, value: Boolean) {
        prefs?.edit()?.putBoolean(key, value)?.apply()
    }
}