package app.qrscan.ui.scanner.presenter

import android.Manifest
import android.content.SharedPreferences
import android.os.Bundle
import app.qrscan.data.repository.QrModelRepository
import app.qrscan.ext.isSoundEnabled
import app.qrscan.ext.isVibrationEnabled
import app.qrscan.ext.saveToGalleryEnabled
import app.qrscan.ext.toQrModel
import app.qrscan.ui.scanner.view.ScannerView
import app.qrscan.util.Constants
import app.qrscan.util.FileManager
import app.qrscan.util.PermissionChecker
import app.qrscan.util.SoundVibrationUtil
import com.journeyapps.barcodescanner.BarcodeResult
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ScannerPresenterImpl(
    private val view: ScannerView,
    private val fileManager: FileManager,
    private val soundVibrationUtil: SoundVibrationUtil,
    private val preferences: SharedPreferences,
    private val repository: QrModelRepository,
    private val permissionChecker: PermissionChecker
): ScannerPresenter, CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.IO
    override var lastScanTime: Long = 0
    override var flashState: Boolean = false

    override fun onViewCreated(arguments: Bundle?) {
    }

    override fun onViewDestroyed() {
        coroutineContext.cancelChildren()
    }

    override fun onScanResult(barcodeResult: BarcodeResult) {
        if (barcodeResult.timestamp - Constants.DIFF_BETWEEN_SCANS_MS < lastScanTime) {
            return
        }
        lastScanTime = barcodeResult.timestamp

        takeIf { preferences.isSoundEnabled() }?.apply { soundVibrationUtil.playSound() }
        takeIf { preferences.isVibrationEnabled() }?.apply { soundVibrationUtil.vibrate() }
        takeIf { preferences.saveToGalleryEnabled() }?.apply {
            fileManager.saveBitmapToGallery(barcodeResult.bitmap)
        }

        launch {
            val qrModel = barcodeResult.result.toQrModel()
            val id = repository.insertQrModel(qrModel)
            withContext(Dispatchers.Main) { view.navigateToDetails(id) }
        }
    }

    override fun onResumeScan() {
        if (permissionChecker.cameraAccessRequired()) {
            view.showCameraRationaleDialog(Manifest.permission.CAMERA)
        } else {
            view.resumeScannerView()
        }
    }

    override fun onPauseScan() {
        view.pauseScannerView()
    }

    override fun onFlashButtonClicked() {
        flashState = flashState.not().also { view.toggleFlashlight(it) }
    }
}
