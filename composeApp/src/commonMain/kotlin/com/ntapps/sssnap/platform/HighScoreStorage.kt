package com.ntapps.sssnap.platform

interface HighScoreStorage {
    fun getHighScore(): Int
    fun saveHighScore(score: Int)
}

expect fun getHighScoreStorage(): HighScoreStorage
