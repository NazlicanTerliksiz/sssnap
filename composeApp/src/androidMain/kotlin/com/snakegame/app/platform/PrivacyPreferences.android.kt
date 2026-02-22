package com.snakegame.app.platform

import android.content.Context
import android.content.SharedPreferences

private const val PREFS_NAME = "snakegame_privacy"
private const val KEY_PRIVACY_ACCEPTED = "privacy_accepted"

internal object PrivacyPreferencesHolder {
    var context: Context? = null
}

private class AndroidPrivacyPreferences(context: Context) : PrivacyPreferences {
    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun isAccepted(): Boolean = prefs.getBoolean(KEY_PRIVACY_ACCEPTED, false)

    override fun setAccepted(accepted: Boolean) {
        prefs.edit().putBoolean(KEY_PRIVACY_ACCEPTED, accepted).apply()
    }
}

actual fun getPrivacyPreferences(): PrivacyPreferences {
    val ctx = PrivacyPreferencesHolder.context
        ?: throw IllegalStateException("PrivacyPreferences not initialized. Call initPrivacyPreferences() from MainActivity.")
    return AndroidPrivacyPreferences(ctx)
}

