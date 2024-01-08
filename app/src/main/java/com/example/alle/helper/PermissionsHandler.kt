package com.example.alle.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class PermissionsHandler(
    private val registry: ActivityResultRegistry,
    private var context: Context
) : DefaultLifecycleObserver {

    // will be used to detect if permissions was requested for first time
    private val preferences =
        context.getSharedPreferences(PERMISSION_PREFERENCE, Context.MODE_PRIVATE)

    private lateinit var requestPermission: ActivityResultLauncher<Array<String>>
    private lateinit var settingsResult: ActivityResultLauncher<Intent>

    private var permissionsInWork: Array<String> = emptyArray()
    private var onGrantedListener: (() -> Unit)? = null
    private var onFailedListener: (() -> Unit)? = null

    override fun onCreate(owner: LifecycleOwner) {
        requestPermission = registry.register(
            REQUEST_KEY,
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions.all { it.value }) {
                onGrantedListener?.invoke()
            } else {
                onFailedListener?.invoke()
            }
        }

        settingsResult = registry.register(
            SETTINGS_KEY,
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (permissionsInWork.checkPermissionGranted()) {
                onGrantedListener?.invoke()
            } else {
                onFailedListener?.invoke()
            }
        }
    }

    private fun Array<String>.checkPermissionGranted(): Boolean {
        return all {
            ActivityCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
//
    private fun Array<String>.validateShouldShowPermissionRationale(activity: Activity): Boolean {
        return any {
            !preferences.getBoolean(
                it,
                false
            ) || ActivityCompat.shouldShowRequestPermissionRationale(activity, it)
        }
    }

    fun requestPermission(
        activity: Activity,
        permissions: Array<String>,
        onGranted: () -> Unit,
        onFailed: () -> Unit = {}
    ) {
        when {
            permissions.checkPermissionGranted() -> onGranted()

            permissions.validateShouldShowPermissionRationale(activity) -> {
                onGrantedListener = onGranted
                onFailedListener = onFailed

                requestPermission.launch(permissions)

                preferences.edit().apply {
                    permissions.onEach { putBoolean(it, true) }
                    apply()
                }
            }

            else -> {
                onGrantedListener = onGranted
                onFailedListener = onFailed

                permissionsInWork = permissions

                settingsResult.launch(
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", activity.packageName, null)
                    }
                )
            }
        }
    }

    companion object {
        private const val PERMISSION_PREFERENCE = "PERMISSION_PREFERENCE"

        private const val REQUEST_KEY = "REQUEST_PERMISSION_KEY"
        private const val SETTINGS_KEY = "SETTINGS_KEY"
    }
}
