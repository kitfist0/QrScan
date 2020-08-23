package app.qrscan.ui.scanner

import app.qrscan.R
import app.qrscan.ui.base.BaseFragment
import app.qrscan.ui.scanner.presenter.ScannerPresenter
import app.qrscan.ui.scanner.view.ScannerView
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_scanner.*

@AndroidEntryPoint
class ScannerFragment: BaseFragment<ScannerPresenter, ScannerView>(R.layout.fragment_scanner) {

    override fun setupViews() {
        super.setupViews()
        flashButton.setOnClickListener {
            presenter.onFlashButtonClicked()
        }
        codeScannerView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult) {
                presenter.onScanResult(result)
            }
            override fun possibleResultPoints(resultPoints: List<ResultPoint>) {
            }
        })
    }

    override fun onResume() {
        super.onResume()
        presenter.onResumeScan()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPauseScan()
    }
}
