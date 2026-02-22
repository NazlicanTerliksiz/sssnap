package com.snakegame.app.game

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Oyun motorunu yöneten sınıf
 */
class GameEngine(
    private val scope: CoroutineScope
) {
    private var boardWidth: Int = GameState.DEFAULT_BOARD_WIDTH
    private var boardHeight: Int = GameState.DEFAULT_BOARD_HEIGHT
    
    private val _gameState = MutableStateFlow(GameState.initial(boardWidth, boardHeight))
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private var gameJob: Job? = null

    // Oyun hızı (milisaniye cinsinden) - düşük değer = hızlı oyun
    private val gameSpeed: Long = 150L

    /**
     * Board boyutunu günceller (ekran boyutuna göre)
     */
    fun updateBoardSize(width: Int, height: Int) {
        if (width != boardWidth || height != boardHeight) {
            boardWidth = width
            boardHeight = height
            // Sadece oyun başlamadıysa güncelle
            if (_gameState.value.status == GameStatus.IDLE) {
                val headImage = _gameState.value.headImage
                _gameState.value = GameState.initial(boardWidth, boardHeight, headImage)
            }
        }
    }

    /**
     * Yılan kafası görselini ayarlar
     */
    fun setSnakeHeadImage(image: ImageBitmap?) {
        _gameState.update { it.copy(headImage = image) }
    }

    /**
     * Oyunu başlatır
     */
    fun startGame() {
        if (_gameState.value.status == GameStatus.RUNNING) return

        // Eğer oyun bittiyse sıfırla
        if (_gameState.value.status == GameStatus.GAME_OVER) {
            resetGame()
        }

        _gameState.update { it.copy(status = GameStatus.RUNNING) }
        startGameLoop()
    }

    /**
     * Oyunu duraklatır
     */
    fun pauseGame() {
        if (_gameState.value.status != GameStatus.RUNNING) return

        gameJob?.cancel()
        _gameState.update { it.copy(status = GameStatus.PAUSED) }
    }

    /**
     * Oyunu devam ettirir
     */
    fun resumeGame() {
        if (_gameState.value.status != GameStatus.PAUSED) return

        _gameState.update { it.copy(status = GameStatus.RUNNING) }
        startGameLoop()
    }

    /**
     * Oyunu sıfırlar
     */
    fun resetGame() {
        gameJob?.cancel()
        val highScore = _gameState.value.highScore
        val headImage = _gameState.value.headImage
        _gameState.value = GameState.initial(boardWidth, boardHeight, headImage).copy(highScore = highScore)
    }

    /**
     * Yılanın yönünü değiştirir
     */
    fun changeDirection(direction: Direction) {
        if (_gameState.value.status != GameStatus.RUNNING) return

        _gameState.update { state ->
            state.copy(snake = state.snake.changeDirection(direction))
        }
    }

    /**
     * Oyun döngüsünü başlatır
     */
    private fun startGameLoop() {
        gameJob?.cancel()
        gameJob = scope.launch {
            while (_gameState.value.status == GameStatus.RUNNING) {
                delay(gameSpeed)
                updateGame()
            }
        }
    }

    /**
     * Oyun durumunu günceller (her tick'te çağrılır)
     */
    private fun updateGame() {
        _gameState.update { state ->
            if (state.status != GameStatus.RUNNING) return@update state

            val snake = state.snake
            val willEatFood = snake.head + snake.direction.delta == state.food

            // Yılanı hareket ettir
            val newSnake = snake.move(grow = willEatFood)

            // Çarpışma kontrolü
            if (newSnake.collidesWithWall(state.boardWidth, state.boardHeight) || newSnake.collidesWithSelf()) {
                val newHighScore = maxOf(state.score, state.highScore)
                return@update state.copy(
                    status = GameStatus.GAME_OVER,
                    highScore = newHighScore
                )
            }

            // Yem yeme kontrolü
            if (willEatFood) {
                val newScore = state.score + 10
                val newFood = GameState.generateFood(newSnake.body, state.boardWidth, state.boardHeight)
                state.copy(
                    snake = newSnake,
                    food = newFood,
                    score = newScore
                )
            } else {
                state.copy(snake = newSnake)
            }
        }
    }
}
