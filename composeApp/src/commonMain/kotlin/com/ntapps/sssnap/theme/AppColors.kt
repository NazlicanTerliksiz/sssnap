package com.ntapps.sssnap.theme

import androidx.compose.ui.graphics.Color

/**
 * Sssnap - Renk Paleti
 */
object AppColors {
    // Ana renkler
    val Primary = Color(0xFFC2E6B3)      // Açık yeşil
    val Secondary = Color(0xFFE6E3B3)    // Açık sarı/bej
    
    // Arka plan renkleri
    val Background = Color(0xFF0D0D0D)   // Koyu siyah
    val Surface = Color(0xFF1A1A1A)      // Koyu gri
    val SurfaceVariant = Color(0xFF252525) // Orta gri
    
    // Yılan renkleri
    val SnakeHead = Primary
    val SnakeBody = Primary.copy(alpha = 0.7f)
    
    // Yem rengi
    val Food = Color(0xFFFFEE8C)
    
    // Metin renkleri
    val TextPrimary = Color.White
    val TextSecondary = Color.White.copy(alpha = 0.6f)
    val TextOnPrimary = Color(0xFF0D0D0D)
    
    // Grid rengi
    val GridLine = Color(0xFF1F1F1F)
}
