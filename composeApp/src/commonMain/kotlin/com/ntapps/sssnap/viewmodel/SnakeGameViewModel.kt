package com.ntapps.sssnap.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntapps.sssnap.game.Direction
import com.ntapps.sssnap.game.GameEngine
import com.ntapps.sssnap.game.GameState
import com.ntapps.sssnap.platform.HighScoreStorage
import kotlinx.coroutines.flow.StateFlow

class SnakeGameViewModel(
    highScoreStorage: HighScoreStorage
) : ViewModel() {
    
    private val gameEngine = GameEngine(viewModelScope, highScoreStorage)
    
    val gameState: StateFlow<GameState> = gameEngine.gameState

    /**
     * Mevcut yılan kafası görselini döndürür
     */
    val currentHeadImage: ImageBitmap?
        get() = gameState.value.headImage

    /**
     * Board boyutunu ekran boyutuna göre günceller
     * Ekranın tam yüksekliğine sığacak şekilde dinamik hesaplama yapar
     */
    fun updateBoardSize(screenWidth: Float, screenHeight: Float) {
        val cellSize = screenWidth / GameState.DEFAULT_BOARD_WIDTH
        val calculatedHeight = kotlin.math.floor(screenHeight / cellSize).toInt()
        gameEngine.updateBoardSize(GameState.DEFAULT_BOARD_WIDTH, calculatedHeight)
    }

    /**
     * Yılan kafası görselini ayarlar
     * Platform-specific kod bu fonksiyonu çağırarak görseli iletir
     */
    fun setSnakeHeadImage(image: ImageBitmap?) {
        gameEngine.setSnakeHeadImage(image)
    }

    /**
     * Oyunu başlatır
     */
    fun startGame() {
        gameEngine.startGame()
    }

    /**
     * Oyunu duraklatır
     */
    fun pauseGame() {
        gameEngine.pauseGame()
    }

    /**
     * Oyunu devam ettirir
     */
    fun resumeGame() {
        gameEngine.resumeGame()
    }

    /**
     * Oyunu sıfırlar
     */
    fun resetGame() {
        gameEngine.resetGame()
    }

    /**
     * Yılanın yönünü değiştirir
     */
    fun changeDirection(direction: Direction) {
        gameEngine.changeDirection(direction)
    }
}
