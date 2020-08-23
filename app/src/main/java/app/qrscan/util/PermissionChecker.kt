package app.qrscan.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION
import androidx.core.content.ContextCompat

class PermissionChecker(private val context: Context) {

    fun cameraAccessRequired(): Boolean =
            VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED

    fun storageAccessRequired(): Boolean =
            VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED

}
