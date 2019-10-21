package org.rohk.humanityinbusiness.utils

import android.content.Context
import org.rohk.humanityinbusiness.R

class PreferenceUtils {
    val APP_DATA = "AppData"

    val PREFERENCES_EMAIL = "login_email"
    val PREFERENCES_USERID = "user_id"
    val PREFERENCES_AVATAR = "avatar_drawable"
    val PREFERENCES_COMMUNITY_ID = "community_id"
    val PREFERENCES_EVENT_ID = "event_id"

    fun getLoginEmail(context: Context): String? {
        val preferences = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE)
        return preferences.getString(PREFERENCES_EMAIL, null)
    }

    fun setLoginEmail(context: Context, email: String) {
        val preferences = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE)
        val preferencesEditor = preferences.edit()
        preferencesEditor.putString(PREFERENCES_EMAIL, email)
        preferencesEditor.apply()
    }

    fun getUserId(context: Context): String {
        val preferences = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE)
        return preferences.getString(PREFERENCES_USERID, "")
    }

    fun setUserId(context: Context, userId: String) {
        val preferences = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE)
        val preferencesEditor = preferences.edit()
        preferencesEditor.putString(PREFERENCES_USERID, userId)
        preferencesEditor.apply()
    }

    fun getSelectedAvatar(context: Context): String {
        val preferences = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE)
        return preferences.getString(PREFERENCES_AVATAR, "")
    }

    fun setSelectedAvatar(context: Context, avatarURL: String) {
        val preferences = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE)
        val preferencesEditor = preferences.edit()
        preferencesEditor.putString(PREFERENCES_AVATAR, avatarURL)
        preferencesEditor.apply()
    }

    fun getSelectedCommunityId(context: Context): String {
        val preferences = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE)
        return preferences.getString(PREFERENCES_COMMUNITY_ID, "0")
    }

    fun setSelectedCommunityId(context: Context, communityId: String) {
        val preferences = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE)
        val preferencesEditor = preferences.edit()
        preferencesEditor.putString(PREFERENCES_COMMUNITY_ID, communityId)
        preferencesEditor.apply()
    }

    fun getSelectedEventId(context: Context): String {
        val preferences = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE)
        return preferences.getString(PREFERENCES_EVENT_ID, "1")
    }

    fun setSelectedEventId(context: Context, eventId: String) {
        val preferences = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE)
        val preferencesEditor = preferences.edit()
        preferencesEditor.putString(PREFERENCES_EVENT_ID, eventId)
        preferencesEditor.apply()
    }
}