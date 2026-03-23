package com.ntapps.sssnap.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ntapps.sssnap.theme.AppColors

@Composable
fun MainMenuScreen(
    onPlayClick: () -> Unit,
    onCustomizeSnakeClick: () -> Unit,
    onPrivacyClick: () -> Unit = {}
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
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Üst boşluk ve başlık
            Spacer(modifier = Modifier.height(200.dp))
            
            Text(
                text = "Sssnap",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.Primary,
                letterSpacing = 6.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            // Butonlar
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 48.dp)
            ) {
                // Oyna butonu
                MinimalButton(
                    text = "Oyna",
                    onClick = onPlayClick,
                    isPrimary = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Özelleştir butonu
                MinimalButton(
                    text = "Yılanımı Seç",
                    onClick = onCustomizeSnakeClick,
                    isPrimary = false
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Gizlilik Politikası",
                    fontSize = 12.sp,
                    color = AppColors.Secondary,
                    letterSpacing = 0.5.sp,
                    modifier = Modifier.clickable(onClick = onPrivacyClick)
                )
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun MinimalButton(
    text: String,
    onClick: () -> Unit,
    isPrimary: Boolean
) {
    val backgroundColor = if (isPrimary) AppColors.Primary else AppColors.Background
    val textColor = if (isPrimary) AppColors.TextOnPrimary else AppColors.Primary
    val borderColor = AppColors.Primary

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(24.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = textColor,
            letterSpacing = 1.sp
        )
    }
}
