package com.snakegame.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform