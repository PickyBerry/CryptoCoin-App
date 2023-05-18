package com.pickyberry.rtuitlab_recruit.presentation.coin_details

import android.app.NotificationManager
import android.content.ComponentName
import android.os.Build
import android.service.notification.NotificationListenerService
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun RequestNotificationsPermission(onPermissionGranted: () -> Unit) {
    val context = LocalContext.current

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            onPermissionGranted()
        } else {
            Toast.makeText(
                context,
                "Cannot post notification without permission",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    LaunchedEffect(Unit) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (notificationManager.isNotificationListenerAccessGranted(ComponentName(context, NotificationListenerService::class.java))) {
                onPermissionGranted()
            } else {
                requestPermissionLauncher.launch("android.permission.POST_NOTIFICATIONS")
            }
        } else {
            onPermissionGranted()
        }
    }
}