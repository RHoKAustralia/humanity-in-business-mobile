package org.rohk.humanityinbusiness.utils

import android.content.Context

class PreferenceUtils {
    val APP_DATA = "AppData"

    val PREFERENCES_EMAIL = "login_email"

    fun getLoginEmail(context: Context): String? {
        val preferences = context.getSharedPreferences(APP_DATA, android.content.Context.MODE_PRIVATE)
        return preferences.getString(PREFERENCES_EMAIL, null)
    }

    fun setLoginEmail(context: Context, email: String) {
        val preferences = context.getSharedPreferences(APP_DATA, android.content.Context.MODE_PRIVATE)
        val preferencesEditor = preferences.edit()
        preferencesEditor.putString(PREFERENCES_EMAIL, email)
        preferencesEditor.apply()
    }
}