package com.ntapps.sssnap

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ntapps.sssnap.navigation.Screen
import com.ntapps.sssnap.navigation.rememberNavigationState
import com.ntapps.sssnap.platform.rememberImagePickerLauncher
import com.ntapps.sssnap.platform.getHighScoreStorage
import com.ntapps.sssnap.platform.getPrivacyPreferences
import com.ntapps.sssnap.screens.GameScreen
import com.ntapps.sssnap.screens.MainMenuScreen
import com.ntapps.sssnap.screens.PrivacyScreen
import com.ntapps.sssnap.screens.SnakeHeadPreviewScreenContent
import com.ntapps.sssnap.screens.SplashScreen
import com.ntapps.sssnap.theme.AppColors
import com.ntapps.sssnap.viewmodel.SnakeGameViewModel

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
        val highScoreStorage = remember { getHighScoreStorage() }
        val viewModel: SnakeGameViewModel = viewModel { SnakeGameViewModel(highScoreStorage) }
        
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
                        viewModel.resetGame()
                        viewModel.startGame()
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
