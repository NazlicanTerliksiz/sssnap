package com.snakegame.app.platform

import android.Manifest
import android.graphics.BitmapFactory
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
actual fun rememberImagePickerLauncher(
    onImagePicked: (ImageBitmap?) -> Unit
): ImagePickerLauncher {
    val context = LocalContext.current
    val activity = context as? androidx.activity.ComponentActivity

    val photoPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val pickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()
                if (bitmap != null) {
                    onImagePicked(bitmap.asImageBitmap())
                } else {
                    onImagePicked(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onImagePicked(null)
            }
        } else {
            onImagePicked(null)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            pickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }

    return remember(pickerLauncher, permissionLauncher) {
        ImagePickerLauncher(
            onLaunch = {
                val hasPermission = ContextCompat.checkSelfPermission(context, photoPermission) ==
                    android.content.pm.PackageManager.PERMISSION_GRANTED
                if (hasPermission) {
                    pickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                } else if (activity != null) {
                    permissionLauncher.launch(photoPermission)
                } else {
                    pickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            }
        )
    }
}

actual class ImagePickerLauncher(
    private val onLaunch: () -> Unit
) {
    actual fun launch() {
        onLaunch()
    }
}
