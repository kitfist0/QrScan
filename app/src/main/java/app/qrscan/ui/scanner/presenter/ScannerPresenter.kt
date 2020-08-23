package app.qrscan.ui.scanner.presenter

import app.qrscan.ui.base.presenter.BasePresenter
import com.journeyapps.barcodescanner.BarcodeResult

interface ScannerPresenter: BasePresenter {

    var lastScanTime: Long

    var flashState: Boolean

    fun onScanResult(barcodeResult: BarcodeResult)

    fun onResumeScan()

    fun onPauseScan()

    fun onFlashButtonClicked()
}
