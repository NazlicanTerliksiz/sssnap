package com.snakegame.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.snakegame.app.navigation.Screen
import com.snakegame.app.navigation.rememberNavigationState
import com.snakegame.app.platform.rememberImagePickerLauncher
import com.snakegame.app.platform.getPrivacyPreferences
import com.snakegame.app.screens.GameScreen
import com.snakegame.app.screens.MainMenuScreen
import com.snakegame.app.screens.PrivacyScreen
import com.snakegame.app.screens.SnakeHeadPreviewScreenContent
import com.snakegame.app.screens.SplashScreen
import com.snakegame.app.theme.AppColors
import com.snakegame.app.viewmodel.SnakeGameViewModel

// Sssnap tema renk şeması
private val DarkColorScheme = darkColorScheme(
    primary = AppColors.Primary,
    secondary = AppColors.Secondary,
    background = AppColors.Background,
    surface = AppColors.Surface,
    onPrimary = AppColors.TextOnPrimary,
    onSecondary = AppColors.TextPrimary,
    onBackground = AppColors.TextPrimary,
    onSurface = AppColors.TextPrimary
)

@Composable
fun App() {
    MaterialTheme(colorScheme = DarkColorScheme) {
        val navigationState = rememberNavigationState()
        val viewModel: SnakeGameViewModel = viewModel { SnakeGameViewModel() }
        
        // Preview ekranı için geçici görsel state
        var previewHeadImage by remember { mutableStateOf<ImageBitmap?>(null) }
        
        // Image picker callback - preview ekranında görseli günceller
        val imagePickerLauncher = rememberImagePickerLauncher { imageBitmap: ImageBitmap? ->
            if (imageBitmap != null) {
                previewHeadImage = imageBitmap
            }
        }

        val privacyPrefs = remember { getPrivacyPreferences() }
        val hasAcceptedPrivacy = remember { privacyPrefs.isAccepted() }

        when (navigationState.currentScreen) {
            is Screen.Splash -> {
                SplashScreen(
                    onSplashFinished = {
                        if (hasAcceptedPrivacy) {
                            navigationState.navigateTo(Screen.MainMenu)
                        } else {
                            navigationState.navigateToPrivacyPolicyFirstLaunch()
                        }
                    }
                )
            }

            is Screen.PrivacyPolicy -> {
                PrivacyScreen(
                    showAcceptButton = navigationState.isFromFirstLaunch(),
                    onAccept = {
                        privacyPrefs.setAccepted(true)
                        navigationState.navigateTo(Screen.MainMenu)
                    },
                    onBack = {
                        navigationState.navigateTo(Screen.MainMenu)
                    }
                )
            }
            
            is Screen.MainMenu -> {
                MainMenuScreen(
                    onPlayClick = {
                        navigationState.navigateTo(Screen.Game)
                    },
                    onCustomizeSnakeClick = {
                        // Preview ekranına geçerken mevcut görseli yükle
                        previewHeadImage = viewModel.currentHeadImage
                        navigationState.navigateTo(Screen.SnakeHeadPreview)
                    },
                    onPrivacyClick = {
                        navigationState.navigateToPrivacyPolicyFromMenu()
                    }
                )
            }
            
            is Screen.SnakeHeadPreview -> {
                SnakeHeadPreviewScreenContent(
                    previewImage = previewHeadImage,
                    onSelectFromGallery = {
                        imagePickerLauncher.launch()
                    },
                    onSaveAndReturn = {
                        // Görseli ViewModel'e kaydet
                        viewModel.setSnakeHeadImage(previewHeadImage)
                        navigationState.navigateTo(Screen.MainMenu)
                    },
                    onCancel = {
                        // İptal - görseli kaydetmeden geri dön
                        previewHeadImage = null
                        navigationState.navigateTo(Screen.MainMenu)
                    }
                )
            }
            
            is Screen.Game -> {
                GameScreen(
                    viewModel = viewModel,
                    onBackToMenu = {
                        navigationState.navigateTo(Screen.MainMenu)
                    }
                )
            }
        }
    }
}
