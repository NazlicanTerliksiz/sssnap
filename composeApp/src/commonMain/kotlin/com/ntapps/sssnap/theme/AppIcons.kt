package com.ntapps.sssnap.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object AppIcons {

    val Home: ImageVector by lazy {
        ImageVector.Builder(
            name = "Home",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(160f, 840f)
                verticalLineTo(360f)
                lineToRelative(320f, -240f)
                lineToRelative(320f, 240f)
                verticalLineTo(840f)
                lineTo(520f, 840f)
                verticalLineTo(600f)
                horizontalLineTo(440f)
                verticalLineTo(840f)
                lineTo(160f, 840f)
                close()
            }
        }.build()
    }

    val Back: ImageVector by lazy {
        ImageVector.Builder(
            name = "Back",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(400f, 880f)
                lineTo(0f, 480f)
                lineToRelative(400f, -400f)
                lineToRelative(71f, 71f)
                lineToRelative(-329f, 329f)
                lineToRelative(329f, 329f)
                lineToRelative(-71f, 71f)
                close()
            }
        }.build()
    }

    val Pause: ImageVector by lazy {
        ImageVector.Builder(
            name = "Pause",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(360f, 640f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(-320f)
                horizontalLineToRelative(-80f)
                verticalLineToRelative(320f)
                close()
                moveTo(520f, 640f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(-320f)
                horizontalLineToRelative(-80f)
                verticalLineToRelative(320f)
                close()
                moveTo(480f, 880f)
                quadToRelative(-83f, 0f, -156f, -31.5f)
                reflectiveQuadTo(197f, 763f)
                quadToRelative(-54f, -54f, -85.5f, -127f)
                reflectiveQuadTo(80f, 480f)
                quadToRelative(0f, -83f, 31.5f, -156f)
                reflectiveQuadTo(197f, 197f)
                quadToRelative(54f, -54f, 127f, -85.5f)
                reflectiveQuadTo(480f, 80f)
                quadToRelative(83f, 0f, 156f, 31.5f)
                reflectiveQuadTo(763f, 197f)
                quadToRelative(54f, 54f, 85.5f, 127f)
                reflectiveQuadTo(880f, 480f)
                quadToRelative(0f, 83f, -31.5f, 156f)
                reflectiveQuadTo(763f, 763f)
                quadToRelative(-54f, 54f, -127f, 85.5f)
                reflectiveQuadTo(480f, 880f)
                close()
                moveTo(480f, 800f)
                quadToRelative(134f, 0f, 227f, -93f)
                reflectiveQuadToRelative(93f, -227f)
                quadToRelative(0f, -134f, -93f, -227f)
                reflectiveQuadToRelative(-227f, -93f)
                quadToRelative(-134f, 0f, -227f, 93f)
                reflectiveQuadToRelative(-93f, 227f)
                quadToRelative(0f, 134f, 93f, 227f)
                reflectiveQuadToRelative(227f, 93f)
                close()
            }
        }.build()
    }

    val Food: ImageVector by lazy {
        ImageVector.Builder(
            name = "Food",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(160f, 840f)
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(80f, 760f)
                verticalLineToRelative(-120f)
                horizontalLineToRelative(800f)
                verticalLineToRelative(120f)
                quadToRelative(0f, 33f, -23.5f, 56.5f)
                reflectiveQuadTo(800f, 840f)
                lineTo(160f, 840f)
                close()
                moveTo(160f, 720f)
                verticalLineToRelative(40f)
                horizontalLineToRelative(640f)
                verticalLineToRelative(-40f)
                lineTo(160f, 720f)
                close()
                moveTo(423f, 560f)
                quadToRelative(-21f, 20f, -77f, 20f)
                reflectiveQuadToRelative(-76f, -20f)
                quadToRelative(-20f, -20f, -56f, -20f)
                reflectiveQuadToRelative(-57f, 20f)
                quadToRelative(-21f, 20f, -77f, 20f)
                verticalLineToRelative(-80f)
                quadToRelative(36f, 0f, 57f, -20f)
                reflectiveQuadToRelative(77f, -20f)
                quadToRelative(56f, 0f, 76f, 20f)
                reflectiveQuadToRelative(56f, 20f)
                quadToRelative(36f, 0f, 57f, -20f)
                reflectiveQuadToRelative(77f, -20f)
                quadToRelative(56f, 0f, 77f, 20f)
                reflectiveQuadToRelative(57f, 20f)
                quadToRelative(36f, 0f, 56f, -20f)
                reflectiveQuadToRelative(76f, -20f)
                quadToRelative(56f, 0f, 79f, 20f)
                reflectiveQuadToRelative(55f, 20f)
                verticalLineToRelative(80f)
                quadToRelative(-56f, 0f, -75f, -20f)
                reflectiveQuadToRelative(-55f, -20f)
                quadToRelative(-36f, 0f, -58f, 20f)
                reflectiveQuadToRelative(-78f, 20f)
                quadToRelative(-56f, 0f, -77f, -20f)
                reflectiveQuadToRelative(-57f, -20f)
                quadToRelative(-36f, 0f, -57f, 20f)
                close()
                moveTo(80f, 400f)
                verticalLineToRelative(-40f)
                quadToRelative(0f, -115f, 108.5f, -177.5f)
                reflectiveQuadTo(480f, 120f)
                quadToRelative(183f, 0f, 291.5f, 62.5f)
                reflectiveQuadTo(880f, 360f)
                verticalLineToRelative(40f)
                lineTo(80f, 400f)
                close()
                moveTo(480f, 200f)
                quadToRelative(-124f, 0f, -207.5f, 31f)
                reflectiveQuadTo(166f, 320f)
                horizontalLineToRelative(628f)
                quadToRelative(-23f, -58f, -106.5f, -89f)
                reflectiveQuadTo(480f, 200f)
                close()
            }
        }.build()
    }

    val Play: ImageVector by lazy {
        ImageVector.Builder(
            name = "Play",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveToRelative(380f, 660f)
                lineToRelative(280f, -180f)
                lineToRelative(-280f, -180f)
                verticalLineToRelative(360f)
                close()
                moveTo(480f, 880f)
                quadToRelative(-83f, 0f, -156f, -31.5f)
                reflectiveQuadTo(197f, 763f)
                quadToRelative(-54f, -54f, -85.5f, -127f)
                reflectiveQuadTo(80f, 480f)
                quadToRelative(0f, -83f, 31.5f, -156f)
                reflectiveQuadTo(197f, 197f)
                quadToRelative(54f, -54f, 127f, -85.5f)
                reflectiveQuadTo(480f, 80f)
                quadToRelative(83f, 0f, 156f, 31.5f)
                reflectiveQuadTo(763f, 197f)
                quadToRelative(54f, 54f, 85.5f, 127f)
                reflectiveQuadTo(880f, 480f)
                quadToRelative(0f, 83f, -31.5f, 156f)
                reflectiveQuadTo(763f, 763f)
                quadToRelative(-54f, 54f, -127f, 85.5f)
                reflectiveQuadTo(480f, 880f)
                close()
                moveTo(480f, 800f)
                quadToRelative(134f, 0f, 227f, -93f)
                reflectiveQuadToRelative(93f, -227f)
                quadToRelative(0f, -134f, -93f, -227f)
                reflectiveQuadToRelative(-227f, -93f)
                quadToRelative(-134f, 0f, -227f, 93f)
                reflectiveQuadToRelative(-93f, 227f)
                quadToRelative(0f, 134f, 93f, 227f)
                reflectiveQuadToRelative(227f, 93f)
                close()
            }
        }.build()
    }
}
