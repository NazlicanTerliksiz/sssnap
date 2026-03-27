package com.ntapps.sssnap.platform

import android.content.Context

private const val PREFS_NAME = "snakegame_scores"
private const val KEY_HIGH_SCORE = "high_score"

private class AndroidHighScoreStorage(context: Context) : HighScoreStorage {
    private val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun getHighScore(): Int = prefs.getInt(KEY_HIGH_SCORE, 0)

    override fun saveHighScore(score: Int) {
        prefs.edit().putInt(KEY_HIGH_SCORE, score).apply()
    }
}

actual fun getHighScoreStorage(): HighScoreStorage {
    val ctx = PrivacyPreferencesHolder.context
        ?: throw IllegalStateException("HighScoreStorage not initialized. Set PrivacyPreferencesHolder.context from MainActivity.")
    return AndroidHighScoreStorage(ctx)
}
