package com.ntapps.sssnap.game

import androidx.compose.ui.graphics.ImageBitmap
import com.ntapps.sssnap.platform.HighScoreStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameEngine(
    private val scope: CoroutineScope,
    private val highScoreStorage: HighScoreStorage
) {
    private var boardWidth: Int = GameState.DEFAULT_BOARD_WIDTH
    private var boardHeight: Int = GameState.DEFAULT_BOARD_HEIGHT
    
    private val _gameState = MutableStateFlow(
        GameState.initial(boardWidth, boardHeight).copy(highScore = highScoreStorage.getHighScore())
    )
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private var gameJob: Job? = null
    private var pendingDirection: Direction? = null

    private val initialSnakeLength = 3

    /**
     * Board boyutunu günceller (ekran boyutuna göre)
     */
    fun updateBoardSize(width: Int, height: Int) {
        if (width != boardWidth || height != boardHeight) {
            boardWidth = width
            boardHeight = height
            _gameState.update { state ->
                if (state.status == GameStatus.IDLE) {
                    GameState.initial(boardWidth, boardHeight, state.headImage)
                } else {
                    state.copy(boardWidth = boardWidth, boardHeight = boardHeight)
                }
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
        pendingDirection = null
        val highScore = _gameState.value.highScore
        val headImage = _gameState.value.headImage
        _gameState.value = GameState.initial(boardWidth, boardHeight, headImage).copy(highScore = highScore)
    }

    /**
     * Yılanın yönünü değiştirir
     */
    fun changeDirection(direction: Direction) {
        if (_gameState.value.status != GameStatus.RUNNING) return
        
        val currentDir = pendingDirection ?: _gameState.value.snake.direction
        if (!currentDir.isOpposite(direction) && currentDir != direction) {
            pendingDirection = direction
        }
    }

    /**
     * Oyun döngüsünü başlatır
     */
    private fun calculateSpeed(snakeLength: Int): Long {
        val foodEaten = (snakeLength - initialSnakeLength).coerceAtLeast(0)
        val speed = GameConstants.INITIAL_SPEED_MS - foodEaten * GameConstants.SPEED_DECREASE_PER_FOOD
        return speed.coerceAtLeast(GameConstants.MIN_SPEED_MS)
    }

    private fun startGameLoop() {
        gameJob?.cancel()
        gameJob = scope.launch {
            while (_gameState.value.status == GameStatus.RUNNING) {
                delay(calculateSpeed(_gameState.value.snake.body.size))
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

            // Bekleyen yön değişikliğini uygula
            val snake = pendingDirection?.let { dir ->
                pendingDirection = null
                state.snake.copy(direction = dir)
            } ?: state.snake

            val willEatFood = snake.head + snake.direction.delta == state.food
            val newSnake = snake.move(grow = willEatFood)

            if (newSnake.collidesWithWall(state.boardWidth, state.boardHeight) || newSnake.collidesWithSelf()) {
                pendingDirection = null
                val newHighScore = maxOf(state.score, state.highScore)
                if (newHighScore > state.highScore) {
                    highScoreStorage.saveHighScore(newHighScore)
                }
                return@update state.copy(
                    snake = snake,
                    status = GameStatus.GAME_OVER,
                    highScore = newHighScore
                )
            }

            if (willEatFood) {
                val newScore = state.score + 10
                val newFood = GameState.generateFood(newSnake.body, state.boardWidth, state.boardHeight)
                state.copy(snake = newSnake, food = newFood, score = newScore)
            } else {
                state.copy(snake = newSnake)
            }
        }
    }
}
