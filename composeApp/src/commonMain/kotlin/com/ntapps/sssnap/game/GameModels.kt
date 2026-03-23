package com.ntapps.sssnap.game

import androidx.compose.ui.graphics.ImageBitmap

/**
 * Oyun tahtasında bir pozisyonu temsil eder
 */
data class Position(
    val x: Int,
    val y: Int
) {
    operator fun plus(other: Position) = Position(x + other.x, y + other.y)
}

/**
 * Yılanın hareket yönü
 */
enum class Direction(val delta: Position) {
    UP(Position(0, -1)),
    DOWN(Position(0, 1)),
    LEFT(Position(-1, 0)),
    RIGHT(Position(1, 0));

    fun isOpposite(other: Direction): Boolean {
        return when (this) {
            UP -> other == DOWN
            DOWN -> other == UP
            LEFT -> other == RIGHT
            RIGHT -> other == LEFT
        }
    }
}

/**
 * Oyunun mevcut durumu
 */
enum class GameStatus {
    IDLE,       // Oyun başlamadı
    RUNNING,    // Oyun devam ediyor
    PAUSED,     // Oyun duraklatıldı
    GAME_OVER   // Oyun bitti
}

/**
 * Yılanı temsil eder
 */
data class Snake(
    val body: List<Position>,
    val direction: Direction
) {
    val head: Position get() = body.first()

    fun move(grow: Boolean = false): Snake {
        val newHead = head + direction.delta
        val newBody = if (grow) {
            listOf(newHead) + body
        } else {
            listOf(newHead) + body.dropLast(1)
        }
        return copy(body = newBody)
    }

    fun changeDirection(newDirection: Direction): Snake {
        // Ters yöne gidemez
        return if (!direction.isOpposite(newDirection)) {
            copy(direction = newDirection)
        } else {
            this
        }
    }

    fun collidesWithSelf(): Boolean {
        return body.drop(1).contains(head)
    }

    fun collidesWithWall(boardWidth: Int, boardHeight: Int): Boolean {
        return head.x < 0 || head.x >= boardWidth || head.y < 0 || head.y >= boardHeight
    }
}

/**
 * Oyunun tüm durumunu tutan state
 */
data class GameState(
    val snake: Snake,
    val food: Position,
    val score: Int,
    val highScore: Int,
    val status: GameStatus,
    val boardWidth: Int,
    val boardHeight: Int,
    val headImage: ImageBitmap? = null  // Özel yılan kafası görseli
) {
    companion object {
        const val DEFAULT_BOARD_WIDTH = 18
        const val DEFAULT_BOARD_HEIGHT = 40

        fun initial(
            boardWidth: Int = DEFAULT_BOARD_WIDTH,
            boardHeight: Int = DEFAULT_BOARD_HEIGHT,
            headImage: ImageBitmap? = null
        ): GameState {
            val centerX = boardWidth / 2
            val centerY = boardHeight / 2

            val initialSnake = Snake(
                body = listOf(
                    Position(centerX, centerY),
                    Position(centerX - 1, centerY),
                    Position(centerX - 2, centerY)
                ),
                direction = Direction.RIGHT
            )

            return GameState(
                snake = initialSnake,
                food = generateFood(initialSnake.body, boardWidth, boardHeight),
                score = 0,
                highScore = 0,
                status = GameStatus.IDLE,
                boardWidth = boardWidth,
                boardHeight = boardHeight,
                headImage = headImage
            )
        }

        fun generateFood(snakeBody: List<Position>, boardWidth: Int, boardHeight: Int): Position {
            val sideMargin = minOf(GameConstants.FOOD_SIDE_MARGIN, boardWidth / 4)
            val verticalMargin = minOf(GameConstants.FOOD_VERTICAL_MARGIN, boardHeight / 4)

            val innerXRange = sideMargin until (boardWidth - sideMargin)
            val innerYRange = verticalMargin until (boardHeight - verticalMargin)

            val preferredPositions = innerXRange.flatMap { x ->
                innerYRange.map { y -> Position(x, y) }
            } - snakeBody.toSet()

            if (preferredPositions.isNotEmpty()) {
                return preferredPositions.random()
            }

            val fallbackPositions = (0 until boardWidth).flatMap { x ->
                (0 until boardHeight).map { y -> Position(x, y) }
            } - snakeBody.toSet()

            return fallbackPositions.random()
        }
    }
}
