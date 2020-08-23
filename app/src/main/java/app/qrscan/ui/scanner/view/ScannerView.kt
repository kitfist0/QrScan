package app.qrscan.ui.scanner.view

import androidx.navigation.NavController
import app.qrscan.ui.base.view.BaseView

interface ScannerView: BaseView {

    val navController: NavController

    fun resumeScannerView()

    fun pauseScannerView()

    fun showCameraRationaleDialog(permission: String)

    fun toggleFlashlight(state: Boolean)

    fun navigateToDetails(id: Long)
}
