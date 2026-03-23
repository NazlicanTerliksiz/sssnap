package com.ntapps.sssnap.game

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.ntapps.sssnap.theme.AppColors
import kotlin.math.min

private object SnakeAppearance {
    const val HEAD_THICKNESS_FACTOR = 0.40f
    const val MIN_TAIL_THICKNESS_FACTOR = 0.26f
    const val CUSTOM_HEAD_PADDING_FACTOR = 0.04f
}

fun DrawScope.drawSnakeSegments(
    snake: Snake,
    cellSize: Float,
    headImage: ImageBitmap? = null,
    origin: Offset = Offset.Zero
) {
    val totalSegments = snake.body.size

    // 1) Segmentler arası bağlantı dikdörtgenleri (kuyruktan kafaya)
    for (i in totalSegments - 1 downTo 1) {
        val cur = snake.body[i]
        val prev = snake.body[i - 1]
        val curR = segmentThickness(i, totalSegments, cellSize)
        val prevR = segmentThickness(i - 1, totalSegments, cellSize)

        val curCx = origin.x + (cur.x + 0.5f) * cellSize
        val curCy = origin.y + (cur.y + 0.5f) * cellSize
        val prevCx = origin.x + (prev.x + 0.5f) * cellSize
        val prevCy = origin.y + (prev.y + 0.5f) * cellSize

        val minX = min(curCx, prevCx)
        val minY = min(curCy, prevCy)

        if (cur.y == prev.y) {
            drawRoundRect(
                color = AppColors.SnakeBody,
                topLeft = Offset(minX, minY - min(curR, prevR)),
                size = Size(cellSize, min(curR, prevR) * 2),
                cornerRadius = CornerRadius(min(curR, prevR))
            )
        } else if (cur.x == prev.x) {
            drawRoundRect(
                color = AppColors.SnakeBody,
                topLeft = Offset(minX - min(curR, prevR), minY),
                size = Size(min(curR, prevR) * 2, cellSize),
                cornerRadius = CornerRadius(min(curR, prevR))
            )
        }
    }

    // 2) Her segmentin köşesine yuvarlak (köşe geçişleri + kafa/kuyruk ucu)
    for (i in totalSegments - 1 downTo 0) {
        val position = snake.body[i]
        val cx = origin.x + (position.x + 0.5f) * cellSize
        val cy = origin.y + (position.y + 0.5f) * cellSize

        if (i == 0 && headImage != null) {
            val radius = segmentThickness(0, totalSegments, cellSize)
            val circlePath = Path().apply {
                addOval(
                    androidx.compose.ui.geometry.Rect(
                        cx - radius, cy - radius, cx + radius, cy + radius
                    )
                )
            }
            clipPath(circlePath) {
                val diameter = (radius * 2).toInt()
                drawImage(
                    image = headImage,
                    dstOffset = IntOffset(
                        (cx - radius).toInt(),
                        (cy - radius).toInt()
                    ),
                    dstSize = IntSize(diameter, diameter)
                )
            }
        } else {
            val radius = segmentThickness(i, totalSegments, cellSize)
            val color = if (i == 0) AppColors.SnakeHead else AppColors.SnakeBody
            drawCircle(color = color, radius = radius, center = Offset(cx, cy))
        }
    }
}

private fun segmentThickness(index: Int, totalSegments: Int, cellSize: Float): Float {
    if (index == 0) return cellSize * SnakeAppearance.HEAD_THICKNESS_FACTOR

    val maxThickness = cellSize * SnakeAppearance.HEAD_THICKNESS_FACTOR
    val minThickness = cellSize * SnakeAppearance.MIN_TAIL_THICKNESS_FACTOR

    val t = index.toFloat() / (totalSegments - 1).coerceAtLeast(1)
    return maxThickness - t * (maxThickness - minThickness)
}
