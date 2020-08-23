package app.qrscan.ui.scanner.view

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import app.qrscan.R
import app.qrscan.ui.scanner.ScannerFragmentDirections
import kotlinx.android.synthetic.main.fragment_scanner.view.*

class ScannerViewImpl(
    override var context: Context,
    override val navController: NavController
) : ScannerView {

    override lateinit var rootView: View

    override fun resumeScannerView() {
        rootView.codeScannerView?.resume()
    }

    override fun pauseScannerView() {
        rootView.codeScannerView?.pause()
    }

    override fun showCameraRationaleDialog(permission: String) {
        try {
            navController.navigate(ScannerFragmentDirections.fromScannerToPermission(permission))
        } catch (e: IllegalArgumentException) {
        }
    }

    override fun toggleFlashlight(state: Boolean) {
        try {
            if (state) {
                rootView.codeScannerView.setTorchOn()
                rootView.flashButton.setImageResource(R.drawable.ic_flash_on)
            } else {
                rootView.codeScannerView.setTorchOff()
                rootView.flashButton.setImageResource(R.drawable.ic_flash_off)
            }
        } catch (e: Exception) {
        }
    }

    override fun navigateToDetails(id: Long) {
        navController.navigate(ScannerFragmentDirections.fromScannerToDetails(id))
    }

    override fun showError(message: String) {
    }

    override fun showError(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
