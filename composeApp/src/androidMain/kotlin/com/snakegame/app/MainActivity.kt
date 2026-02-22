package com.snakegame.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.snakegame.app.platform.PrivacyPreferencesHolder
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Sistem splash'ini anında kapat (super.onCreate'den ÖNCE çağrılmalı)
        installSplashScreen()
        
        super.onCreate(savedInstanceState)
        PrivacyPreferencesHolder.context = applicationContext

        // Tam ekran modu - status bar ve navigation bar gizle
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        setContent {
            App()
        }
    }
}