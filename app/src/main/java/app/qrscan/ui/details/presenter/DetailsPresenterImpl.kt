package app.qrscan.ui.details.presenter

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import app.qrscan.R
import app.qrscan.data.db.QrModel
import app.qrscan.data.repository.QrModelRepository
import app.qrscan.ui.details.DetailsFragmentArgs
import app.qrscan.ui.details.view.DetailsView
import app.qrscan.util.EncodeUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailsPresenterImpl(
    private val view: DetailsView,
    private val clipboard: ClipboardManager,
    private val repository: QrModelRepository,
    private val encodeUtil: EncodeUtil
): DetailsPresenter, CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.IO
    override lateinit var qrModel: QrModel

    override fun onViewCreated(arguments: Bundle?) {
        val id = arguments?.let { DetailsFragmentArgs.fromBundle(it).id } ?: 0
        launch {
            qrModel = repository.getQrModelWithId(id)
            val bitmap = encodeUtil.modelToBitmap(qrModel)
            withContext(Dispatchers.Main) {
                view.setupToolbarTitle(qrModel.title)
                view.setupEncodedText(qrModel.text)
                view.setupQrImage(bitmap)
                view.setupFavoriteIcon(qrModel.favorite)
            }
        }
    }

    override fun onViewDestroyed() {
        coroutineContext.cancelChildren()
    }

    override fun onCopyButtonClicked() {
        clipboard.setPrimaryClip(ClipData.newPlainText("", qrModel.text))
        view.showMessage(R.string.copied_to_clipboard)
    }

    override fun onEditTitleClicked() {
        view.navigateToEditTitleDialog(qrModel.title)
    }

    override fun onDeleteButtonClicked() {
        launch {
            repository.deleteQrModel(qrModel)
            withContext(Dispatchers.Main) { view.popBackStack() }
        }
    }

    override fun onFavoriteButtonClicked() {
        launch {
            val favorite = repository.updateAndGetFavorite(qrModel.id).also { qrModel.favorite = it }
            withContext(Dispatchers.Main) { view.setupFavoriteIcon(favorite) }
        }
    }

    override fun onShareButtonClicked() {
        // TODO: show share dialog
        view.showMessage(R.string.share)
    }

    override fun onTitleChangedResult(result: Bundle) {
    }
}
