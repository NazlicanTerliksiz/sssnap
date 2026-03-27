package com.ntapps.sssnap.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ntapps.sssnap.game.*
import com.ntapps.sssnap.theme.AppColors
import com.ntapps.sssnap.theme.AppIcons
import com.ntapps.sssnap.viewmodel.SnakeGameViewModel

@Composable
fun GameScreen(
    viewModel: SnakeGameViewModel,
    onBackToMenu: () -> Unit
) {
    val gameState by viewModel.gameState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        // Oyun alanı - tam ekran (insets göz ardı)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { size ->
                    viewModel.updateBoardSize(size.width.toFloat(), size.height.toFloat())
                }
                .swipeGesture { direction ->
                    viewModel.changeDirection(direction)
                }
        ) {
            GameBoard(
                gameState = gameState,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Üst bar - skor ve menü (overlay, safe area ile)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 32.dp)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            // Skor
            Column {
                Text(
                    text = gameState.score.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppColors.Primary
                )
                Text(
                    text = "skor",
                    fontSize = 12.sp,
                    color = AppColors.TextSecondary,
                    letterSpacing = 1.sp
                )
            }

            // En yüksek skor
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = gameState.highScore.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppColors.Secondary
                )
                Text(
                    text = "en iyi",
                    fontSize = 12.sp,
                    color = AppColors.TextSecondary,
                    letterSpacing = 1.sp
                )
            }

            // Ev ikonu (home) - skor text ile aynı hizada
            Box(
                modifier = Modifier.height(34.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                HomeIcon(onClick = {
                    viewModel.pauseGame()
                    onBackToMenu()
                })
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
                        onResume = { viewModel.resumeGame() }
                    )
                }
                GameStatus.GAME_OVER -> {
                    GameOverOverlay(
                        score = gameState.score,
                        highScore = gameState.highScore,
                        onRestart = { viewModel.startGame() }
                    )
                }
                GameStatus.RUNNING -> {
                    // Sağ alt köşede pause butonu
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding()
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
    Icon(
        imageVector = AppIcons.Play,
        contentDescription = "Play",
        tint = AppColors.Primary,
        modifier = Modifier
            .size(56.dp)
            .clickable(onClick = onClick)
    )
}

@Composable
private fun PauseButton(onClick: () -> Unit) {
    Icon(
        imageVector = AppIcons.Pause,
        contentDescription = "Pause",
        tint = AppColors.Primary,
        modifier = Modifier
            .size(44.dp)
            .clickable(onClick = onClick)
    )
}

@Composable
private fun HomeIcon(onClick: () -> Unit) {
    Icon(
        imageVector = AppIcons.Home,
        contentDescription = "Home",
        tint = AppColors.Primary,
        modifier = Modifier
            .size(32.dp)
            .clickable(onClick = onClick)
    )
}

@Composable
private fun PausedOverlay(
    onResume: () -> Unit
) {
    Icon(
        imageVector = AppIcons.Play,
        contentDescription = "Resume",
        tint = AppColors.Primary,
        modifier = Modifier
            .size(56.dp)
            .clickable(onClick = onResume)
    )
}

@Composable
private fun GameOverOverlay(
    score: Int,
    highScore: Int,
    onRestart: () -> Unit
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
                fontSize = 14.sp,
                color = AppColors.Secondary,
                letterSpacing = 2.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Icon(
            imageVector = AppIcons.Play,
            contentDescription = "Restart",
            tint = AppColors.Primary,
            modifier = Modifier
                .size(56.dp)
                .clickable(onClick = onRestart)
        )
    }
}
