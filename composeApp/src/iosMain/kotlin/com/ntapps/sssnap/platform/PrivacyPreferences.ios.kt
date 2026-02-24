package com.ntapps.sssnap.platform

import platform.Foundation.NSUserDefaults

private const val KEY_PRIVACY_ACCEPTED = "privacy_accepted"

private class IosPrivacyPreferences : PrivacyPreferences {
    private val defaults = NSUserDefaults.standardUserDefaults

    override fun isAccepted(): Boolean =
        defaults.boolForKey(KEY_PRIVACY_ACCEPTED)

    override fun setAccepted(accepted: Boolean) {
        defaults.setBool(accepted, KEY_PRIVACY_ACCEPTED)
    }
}

actual fun getPrivacyPreferences(): PrivacyPreferences = IosPrivacyPreferences()
