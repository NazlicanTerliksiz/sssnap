package com.snakegame.app.game

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.abs

/**
 * Swipe gesture ile yön kontrolü sağlayan modifier
 */
fun Modifier.swipeGesture(
    onDirectionChange: (Direction) -> Unit
): Modifier = this.pointerInput(Unit) {
    detectDragGestures { change, dragAmount ->
        change.consume()

        val (x, y) = dragAmount
        if (abs(x) > abs(y)) {
            if (x > 0) {
                onDirectionChange(Direction.RIGHT)
            } else {
                onDirectionChange(Direction.LEFT)
            }
        } else {
            if (y > 0) {
                onDirectionChange(Direction.DOWN)
            } else {
                onDirectionChange(Direction.UP)
            }
        }
    }
}
