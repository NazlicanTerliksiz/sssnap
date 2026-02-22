package com.snakegame.app.navigation

/**
 * Uygulamadaki ekranları temsil eden sealed class
 */
sealed class Screen {
    data object Splash : Screen()
    data object PrivacyPolicy : Screen()
    data object MainMenu : Screen()
    data object SnakeHeadPreview : Screen()
    data object Game : Screen()
}
