package com.ntapps.sssnap.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * Basit navigation state yönetimi
 */
class NavigationState {
    var currentScreen by mutableStateOf<Screen>(Screen.Splash)
        private set

    /** Gizlilik ekranı ilk açılışta mı (Kabul butonu göster) yoksa menüden mi açıldı */
    var privacyScreenShowAccept by mutableStateOf(true)
        private set

    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }

    fun navigateToPrivacyPolicyFirstLaunch() {
        privacyScreenShowAccept = true
        currentScreen = Screen.PrivacyPolicy
    }

    fun navigateToPrivacyPolicyFromMenu() {
        privacyScreenShowAccept = false
        currentScreen = Screen.PrivacyPolicy
    }

    fun isFromFirstLaunch(): Boolean = privacyScreenShowAccept
}

@Composable
fun rememberNavigationState(): NavigationState {
    return remember { NavigationState() }
}
