package com.ntapps.sssnap.platform

import platform.Foundation.NSUserDefaults

private const val KEY_HIGH_SCORE = "high_score"

private class IosHighScoreStorage : HighScoreStorage {
    private val defaults = NSUserDefaults.standardUserDefaults

    override fun getHighScore(): Int = defaults.integerForKey(KEY_HIGH_SCORE).toInt()

    override fun saveHighScore(score: Int) {
        defaults.setInteger(score.toLong(), KEY_HIGH_SCORE)
    }
}

actual fun getHighScoreStorage(): HighScoreStorage = IosHighScoreStorage()
