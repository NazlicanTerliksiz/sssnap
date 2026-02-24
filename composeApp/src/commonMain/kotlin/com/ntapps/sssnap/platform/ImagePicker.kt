package com.ntapps.sssnap.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

/**
 * Platform-specific image picker interface
 * Her platform kendi galeri erişim mantığını implement eder
 */
@Composable
expect fun rememberImagePickerLauncher(
    onImagePicked: (ImageBitmap?) -> Unit
): ImagePickerLauncher

/**
 * Image picker'ı başlatmak için kullanılan launcher
 */
expect class ImagePickerLauncher {
    fun launch()
}
