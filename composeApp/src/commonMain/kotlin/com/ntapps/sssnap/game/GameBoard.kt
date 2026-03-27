package com.ntapps.sssnap.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.ntapps.sssnap.theme.AppColors
import com.ntapps.sssnap.theme.AppIcons

@Composable
fun GameBoard(
    gameState: GameState,
    modifier: Modifier = Modifier
) {
    val foodPainter = rememberVectorPainter(image = AppIcons.Food)

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(color = AppColors.Background)
    ) {
        val cellSize = size.width / gameState.boardWidth

        drawGrid(gameState.boardWidth, cellSize, size.height)

        val padding = cellSize * 0.15f
        val iconSize = cellSize - padding * 2
        translate(
            left = gameState.food.x * cellSize + padding,
            top = gameState.food.y * cellSize + padding
        ) {
            with(foodPainter) {
                draw(
                    size = Size(iconSize, iconSize),
                    colorFilter = ColorFilter.tint(AppColors.Food)
                )
            }
        }

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

