package com.ntapps.sssnap.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ntapps.sssnap.game.Direction
import com.ntapps.sssnap.game.Position
import com.ntapps.sssnap.game.Snake
import com.ntapps.sssnap.game.drawSnakeSegments
import com.ntapps.sssnap.theme.AppColors

@Composable
fun SnakeHeadPreviewScreen(
    currentHeadImage: ImageBitmap?,
    onSelectFromGallery: () -> Unit,
    onSaveAndReturn: () -> Unit,
    onCancel: () -> Unit
) {
    var previewImage by remember(currentHeadImage) { mutableStateOf(currentHeadImage) }

    SnakeHeadPreviewScreenContent(
        previewImage = previewImage,
        onSelectFromGallery = onSelectFromGallery,
        onSaveAndReturn = onSaveAndReturn,
        onCancel = onCancel
    )
}

@Composable
fun SnakeHeadPreviewScreenContent(
    previewImage: ImageBitmap?,
    onSelectFromGallery: () -> Unit,
    onSaveAndReturn: () -> Unit,
    onCancel: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Üst bar - sadece geri ikonu
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                // Geri ikonu - daha büyük, arka plan yok
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(onClick = onCancel),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "←",
                        fontSize = 24.sp,
                        color = AppColors.Primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Başlık
            Text(
                text = "Yılanım",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = AppColors.Secondary,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            // Önizleme - küçük yılan
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                contentAlignment = Alignment.Center
            ) {
                SnakePreviewCanvas(
                    headImage = previewImage,
                    modifier = Modifier
                        .width(140.dp)
                        .height(35.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Durum
            Text(
                text = if (previewImage != null) "✓" else "—",
                fontSize = 14.sp,
                color = if (previewImage != null) AppColors.Primary else AppColors.TextSecondary
            )

            Spacer(modifier = Modifier.weight(1f))

            // Butonlar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Fotoğraflarım
                SmallButton(
                    text = "Fotoğraflarım",
                    onClick = onSelectFromGallery,
                    isPrimary = false,
                    modifier = Modifier.weight(1f)
                )

                // Kaydet
                SmallButton(
                    text = "Kaydet",
                    onClick = onSaveAndReturn,
                    isPrimary = true,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun SmallButton(
    text: String,
    onClick: () -> Unit,
    isPrimary: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isPrimary) AppColors.Primary else AppColors.Background
    val textColor = if (isPrimary) AppColors.TextOnPrimary else AppColors.Primary
    val borderColor = AppColors.Primary

    Box(
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = textColor,
            letterSpacing = 1.sp
        )
    }
}

/**
 * Yılan önizleme - 1 kafa + 3 gövde, çok küçük kareler
 */
@Composable
private fun SnakePreviewCanvas(
    headImage: ImageBitmap?,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val cellSize = size.height * 0.9f
        val totalCells = 4 // 1 kafa + 3 gövde
        val startX = (size.width - cellSize * totalCells) / 2
        val startY = (size.height - cellSize) / 2

        val previewSnake = Snake(
            body = listOf(
                Position(3, 0),
                Position(2, 0),
                Position(1, 0),
                Position(0, 0)
            ),
            direction = Direction.RIGHT
        )

        drawSnakeSegments(
            snake = previewSnake,
            cellSize = cellSize,
            headImage = headImage,
            origin = Offset(startX, startY)
        )
    }
}

@Composable
fun SnakeHeadPreviewScreenWithPicker(
    currentHeadImage: ImageBitmap?,
    onImageSelected: (ImageBitmap?) -> Unit,
    onSaveAndReturn: (ImageBitmap?) -> Unit,
    onCancel: () -> Unit,
    imagePickerContent: @Composable (onImagePicked: (ImageBitmap?) -> Unit) -> Unit
) {
    var previewImage by remember(currentHeadImage) { mutableStateOf(currentHeadImage) }
    var showPicker by remember { mutableStateOf(false) }

    if (showPicker) {
        imagePickerContent { image ->
            if (image != null) {
                previewImage = image
                onImageSelected(image)
            }
            showPicker = false
        }
    }

    SnakeHeadPreviewScreenContent(
        previewImage = previewImage,
        onSelectFromGallery = { showPicker = true },
        onSaveAndReturn = { onSaveAndReturn(previewImage) },
        onCancel = onCancel
    )
}
