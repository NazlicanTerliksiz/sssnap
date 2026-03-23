package com.ntapps.sssnap.game

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.abs

private const val SWIPE_THRESHOLD = 30f

fun Modifier.swipeGesture(
    onDirectionChange: (Direction) -> Unit
): Modifier = this.pointerInput(Unit) {
    var totalX = 0f
    var totalY = 0f
    var swiped = false

    detectDragGestures(
        onDragStart = {
            totalX = 0f
            totalY = 0f
            swiped = false
        },
        onDrag = { change, dragAmount ->
            change.consume()
            totalX += dragAmount.x
            totalY += dragAmount.y

            if (!swiped && (abs(totalX) > SWIPE_THRESHOLD || abs(totalY) > SWIPE_THRESHOLD)) {
                swiped = true
                if (abs(totalX) > abs(totalY)) {
                    onDirectionChange(if (totalX > 0) Direction.RIGHT else Direction.LEFT)
                } else {
                    onDirectionChange(if (totalY > 0) Direction.DOWN else Direction.UP)
                }
            }
        }
    )
}
