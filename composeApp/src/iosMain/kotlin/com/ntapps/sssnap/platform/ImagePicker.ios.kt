package com.ntapps.sssnap.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readBytes
import org.jetbrains.skia.Image
import platform.Foundation.NSData
import platform.PhotosUI.PHPickerConfiguration
import platform.PhotosUI.PHPickerConfigurationSelectionOrdered
import platform.PhotosUI.PHPickerFilter
import platform.PhotosUI.PHPickerResult
import platform.PhotosUI.PHPickerViewController
import platform.PhotosUI.PHPickerViewControllerDelegateProtocol
import platform.UIKit.UIApplication
import platform.darwin.NSObject

@Composable
actual fun rememberImagePickerLauncher(
    onImagePicked: (ImageBitmap?) -> Unit
): ImagePickerLauncher {
    return remember {
        ImagePickerLauncher(onImagePicked)
    }
}

actual class ImagePickerLauncher(
    private val onImagePicked: (ImageBitmap?) -> Unit
) {
    actual fun launch() {
        val configuration = PHPickerConfiguration().apply {
            filter = PHPickerFilter.imagesFilter
            selectionLimit = 1
            selection = PHPickerConfigurationSelectionOrdered
        }
        
        val picker = PHPickerViewController(configuration = configuration)
        val delegate = ImagePickerDelegate(onImagePicked)
        picker.delegate = delegate
        
        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        rootViewController?.presentViewController(picker, animated = true, completion = null)
    }
}

private class ImagePickerDelegate(
    private val onImagePicked: (ImageBitmap?) -> Unit
) : NSObject(), PHPickerViewControllerDelegateProtocol {
    
    override fun picker(picker: PHPickerViewController, didFinishPicking: List<*>) {
        picker.dismissViewControllerAnimated(true, completion = null)
        
        val result = didFinishPicking.firstOrNull() as? PHPickerResult
        if (result == null) {
            onImagePicked(null)
            return
        }
        
        result.itemProvider.loadDataRepresentationForTypeIdentifier("public.image") { data, error ->
            if (error != null || data == null) {
                onImagePicked(null)
                return@loadDataRepresentationForTypeIdentifier
            }
            val imageBitmap = data.toImageBitmap()
            onImagePicked(imageBitmap)
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun NSData.toImageBitmap(): ImageBitmap? {
    val length = this.length.toInt()
    if (length <= 0) return null
    val ptr = this.bytes ?: return null
    val bytes = ptr.readBytes(length)
    return try {
        val skiaImage = Image.makeFromEncoded(bytes)
        skiaImage.toComposeImageBitmap()
    } catch (e: Exception) {
        null
    }
}
