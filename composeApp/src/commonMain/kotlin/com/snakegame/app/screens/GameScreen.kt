package com.snakegame.app.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snakegame.app.game.*
import com.snakegame.app.theme.AppColors
import com.snakegame.app.viewmodel.SnakeGameViewModel

@Composable
fun GameScreen(
    viewModel: SnakeGameViewModel,
    onBackToMenu: () -> Unit
) {
    val gameState by viewModel.gameState.collectAsState()
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .onSizeChanged { size ->
                // Ekran boyutuna göre board height hesapla
                viewModel.updateBoardSize(size.width.toFloat(), size.height.toFloat())
            }
            .swipeGesture { direction ->
                viewModel.changeDirection(direction)
            }
    ) {
        // Oyun alanı - tam ekran arka planda
        GameBoard(
            gameState = gameState,
            modifier = Modifier.fillMaxSize()
        )

        // Üst bar - skor ve menü (overlay)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            // Skor
            Column {
                Text(
                    text = gameState.score.toString(),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Primary
                )
                Text(
                    text = "skor",
                    fontSize = 10.sp,
                    color = AppColors.TextSecondary,
                    letterSpacing = 1.sp
                )
            }

            // En yüksek skor
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = gameState.highScore.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppColors.Secondary
                )
                Text(
                    text = "en iyi",
                    fontSize = 10.sp,
                    color = AppColors.TextSecondary,
                    letterSpacing = 1.sp
                )
            }

            // Ev ikonu (home) - skor text ile aynı hizada
            Box(
                modifier = Modifier.height(34.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                HomeIcon(onClick = onBackToMenu)
            }
        }

        // Overlay durumlar
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (gameState.status) {
                GameStatus.IDLE -> {
                    PlayButton(onClick = { viewModel.startGame() })
                }
                GameStatus.PAUSED -> {
                    PausedOverlay(
                        onResume = { viewModel.resumeGame() },
                        onRestart = { viewModel.resetGame() }
                    )
                }
                GameStatus.GAME_OVER -> {
                    GameOverOverlay(
                        score = gameState.score,
                        highScore = gameState.highScore,
                        onRestart = { viewModel.startGame() },
                        onBackToMenu = onBackToMenu
                    )
                }
                GameStatus.RUNNING -> {
                    // Sağ alt köşede pause butonu
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        PauseButton(onClick = { viewModel.pauseGame() })
                    }
                }
            }
        }
    }
}

@Composable
private fun PlayButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(AppColors.Primary)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "▶",
            fontSize = 20.sp,
            color = AppColors.TextOnPrimary
        )
    }
}

@Composable
private fun PauseButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            // İki dikey çizgi (pause ikonu)
            repeat(2) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(20.dp)
                        .background(AppColors.Primary)
                )
            }
        }
    }
}

@Composable
private fun HomeIcon(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(24.dp)) {
            val width = size.width
            val height = size.height
            
            // Ev çatısı (üçgen)
            val roofPath = androidx.compose.ui.graphics.Path().apply {
                moveTo(width / 2, 0f)
                lineTo(0f, height * 0.45f)
                lineTo(width, height * 0.45f)
                close()
            }
            drawPath(roofPath, AppColors.Primary)
            
            // Ev gövdesi (dikdörtgen)
            drawRect(
                color = AppColors.Primary,
                topLeft = Offset(width * 0.15f, height * 0.4f),
                size = Size(width * 0.7f, height * 0.55f)
            )
            
            // Kapı (boşluk)
            drawRect(
                color = AppColors.Background,
                topLeft = Offset(width * 0.35f, height * 0.6f),
                size = Size(width * 0.3f, height * 0.35f)
            )
        }
    }
}

@Composable
private fun PausedOverlay(
    onResume: () -> Unit,
    onRestart: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "duraklatıldı",
            fontSize = 14.sp,
            color = AppColors.TextSecondary,
            letterSpacing = 2.sp
        )
        
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            // Devam
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(AppColors.Primary)
                    .clickable(onClick = onResume),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "▶",
                    fontSize = 18.sp,
                    color = AppColors.TextOnPrimary
                )
            }
            
            // Yeniden başlat
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(AppColors.SurfaceVariant)
                    .clickable(onClick = onRestart),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "↻",
                    fontSize = 18.sp,
                    color = AppColors.Primary
                )
            }
        }
    }
}

@Composable
private fun GameOverOverlay(
    score: Int,
    highScore: Int,
    onRestart: () -> Unit,
    onBackToMenu: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(32.dp)
    ) {
        Text(
            text = score.toString(),
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.Primary
        )
        
        if (score >= highScore && score > 0) {
            Text(
                text = "yeni rekor!",
                fontSize = 12.sp,
                color = AppColors.Secondary,
                letterSpacing = 2.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            // Tekrar oyna
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(AppColors.Primary)
                    .clickable(onClick = onRestart),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "↻",
                    fontSize = 18.sp,
                    color = AppColors.TextOnPrimary
                )
            }

            // Menüye dön (ev ikonu)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(AppColors.SurfaceVariant)
                    .clickable(onClick = onBackToMenu),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(20.dp)) {
                    val width = size.width
                    val height = size.height
                    
                    val roofPath = androidx.compose.ui.graphics.Path().apply {
                        moveTo(width / 2, 0f)
                        lineTo(0f, height * 0.45f)
                        lineTo(width, height * 0.45f)
                        close()
                    }
                    drawPath(roofPath, AppColors.Primary)
                    
                    drawRect(
                        color = AppColors.Primary,
                        topLeft = Offset(width * 0.15f, height * 0.4f),
                        size = Size(width * 0.7f, height * 0.55f)
                    )
                    
                    drawRect(
                        color = AppColors.SurfaceVariant,
                        topLeft = Offset(width * 0.35f, height * 0.6f),
                        size = Size(width * 0.3f, height * 0.35f)
                    )
                }
            }
        }
    }
}
