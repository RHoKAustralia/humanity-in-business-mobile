package org.rohk.humanityinbusiness.utils

import android.content.Context
import org.rohk.humanityinbusiness.R

class PreferenceUtils {
    val APP_DATA = "AppData"

    val PREFERENCES_EMAIL = "login_email"
    val PREFERENCES_USERID = "user_id"
    val PREFERENCES_SDG_GOALS = "sdg_goals"
    val PREFERENCES_SDG_IDS = "sdg_ids"
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

    fun isGoalsSelected(context: Context): Boolean {
        val preferences = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE)
        return preferences.getBoolean(PREFERENCES_SDG_GOALS, false)
    }

    fun setGoalsSelectionStatus(context: Context, goalsStatus: Boolean) {
        val preferences = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE)
        val preferencesEditor = preferences.edit()
        preferencesEditor.putBoolean(PREFERENCES_SDG_GOALS, goalsStatus)
        preferencesEditor.apply()
    }

    fun getSelectedSDGIds(context: Context): String {
        val preferences = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE)
        return preferences.getString(PREFERENCES_SDG_IDS, "")
    }

    fun setSelectedSDGIds(context: Context, sdgIds: String) {
        val preferences = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE)
        val preferencesEditor = preferences.edit()
        preferencesEditor.putString(PREFERENCES_SDG_IDS, sdgIds)
        preferencesEditor.apply()
    }

    fun getSelectedAvatar(context: Context): Int {
        val preferences = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE)
        return preferences.getInt(PREFERENCES_AVATAR, R.drawable.avatar0)
    }

    fun setSelectedAvatar(context: Context, avatarDrawable: Int) {
        val preferences = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE)
        val preferencesEditor = preferences.edit()
        preferencesEditor.putInt(PREFERENCES_AVATAR, avatarDrawable)
        preferencesEditor.apply()
    }

    fun getSelectedCommunityId(context: Context): String {
        val preferences = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE)
        return preferences.getString(PREFERENCES_COMMUNITY_ID, "1")
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