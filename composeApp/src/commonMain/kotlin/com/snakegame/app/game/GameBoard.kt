package com.snakegame.app.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.snakegame.app.theme.AppColors

@Composable
fun GameBoard(
    gameState: GameState,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(color = AppColors.Background)
    ) {
        // Ekran genişliğine göre hücre boyutunu hesapla
        val cellSize = size.width / gameState.boardWidth
        val gridHeight = gameState.boardHeight * cellSize

        // Arka plan - grid alanı
        drawRect(
            color = AppColors.Background,
            topLeft = Offset(0f, 0f),
            size = Size(size.width, gridHeight)
        )

        // Grid çizgileri
        drawGrid(gameState.boardWidth, gameState.boardHeight, cellSize)

        // Yem
        drawFood(gameState.food, cellSize)

        // Yılan
        drawSnake(gameState.snake, cellSize, gameState.headImage)
    }
}

private fun DrawScope.drawGrid(boardWidth: Int, boardHeight: Int, cellSize: Float) {
    val gridHeight = boardHeight * cellSize
    
    // Dikey çizgiler - board yüksekliğine kadar
    for (i in 0..boardWidth) {
        val posX = i * cellSize
        drawLine(
            color = AppColors.GridLine,
            start = Offset(posX, 0f),
            end = Offset(posX, gridHeight),
            strokeWidth = 0.5f
        )
    }
    // Yatay çizgiler - board yüksekliğine kadar
    for (i in 0..boardHeight) {
        val posY = i * cellSize
        drawLine(
            color = AppColors.GridLine,
            start = Offset(0f, posY),
            end = Offset(size.width, posY),
            strokeWidth = 0.5f
        )
    }
}

private fun DrawScope.drawFood(food: Position, cellSize: Float) {
    val padding = cellSize * 0.2f
    
    drawRoundRect(
        color = AppColors.Food,
        topLeft = Offset(
            food.x * cellSize + padding,
            food.y * cellSize + padding
        ),
        size = Size(cellSize - padding * 2, cellSize - padding * 2),
        cornerRadius = CornerRadius(cellSize * 0.3f)
    )
}

private fun DrawScope.drawSnake(snake: Snake, cellSize: Float, headImage: ImageBitmap?) {
    snake.body.forEachIndexed { index, position ->
        val isHead = index == 0
        val padding = cellSize * 0.1f

        if (isHead && headImage != null) {
            drawImage(
                image = headImage,
                dstOffset = IntOffset(
                    (position.x * cellSize + padding).toInt(),
                    (position.y * cellSize + padding).toInt()
                ),
                dstSize = IntSize(
                    (cellSize - padding * 2).toInt(),
                    (cellSize - padding * 2).toInt()
                )
            )
        } else {
            val color = if (isHead) AppColors.SnakeHead else AppColors.SnakeBody
            drawRoundRect(
                color = color,
                topLeft = Offset(
                    position.x * cellSize + padding,
                    position.y * cellSize + padding
                ),
                size = Size(cellSize - padding * 2, cellSize - padding * 2),
                cornerRadius = CornerRadius(cellSize * 0.2f)
            )
        }
    }
}
