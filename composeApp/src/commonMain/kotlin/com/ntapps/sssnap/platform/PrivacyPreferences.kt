package com.ntapps.sssnap.platform

/**
 * Platform-specific storage for "privacy policy accepted" flag.
 * Used to show KVKK screen only on first launch.
 */
interface PrivacyPreferences {
    fun isAccepted(): Boolean
    fun setAccepted(accepted: Boolean)
}

expect fun getPrivacyPreferences(): PrivacyPreferences
