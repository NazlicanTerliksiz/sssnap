package com.ntapps.sssnap.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.ntapps.sssnap.theme.AppColors

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
        val cellSize = size.width / gameState.boardWidth

        // Grid çizgileri - tam ekran yüksekliğinde
        drawGrid(gameState.boardWidth, cellSize, size.height)

        // Yem
        drawFood(gameState.food, cellSize)

        // Yılan
        drawSnakeSegments(
            snake = gameState.snake,
            cellSize = cellSize,
            headImage = gameState.headImage
        )
    }
}

private fun DrawScope.drawGrid(boardWidth: Int, cellSize: Float, screenHeight: Float) {
    val rowCount = kotlin.math.floor(screenHeight / cellSize).toInt()
    val gridHeight = rowCount * cellSize

    for (i in 0..boardWidth) {
        val posX = i * cellSize
        drawLine(
            color = AppColors.GridLine,
            start = Offset(posX, 0f),
            end = Offset(posX, gridHeight),
            strokeWidth = 0.5f
        )
    }

    for (i in 0..rowCount) {
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
