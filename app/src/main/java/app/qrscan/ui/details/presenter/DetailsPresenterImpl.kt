package app.qrscan.ui.details.presenter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import app.qrscan.R
import app.qrscan.data.db.entity.QrModel
import app.qrscan.data.repository.QrModelRepository
import app.qrscan.ui.details.DetailsFragmentArgs
import app.qrscan.ui.details.view.DetailsView
import app.qrscan.util.Constants
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
            val favorite = repository.switchFavorite(qrModel).also { qrModel.favorite = it }
            withContext(Dispatchers.Main) { view.setupFavoriteIcon(favorite) }
        }
    }

    override fun onShareButtonClicked() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, qrModel.text)
            type = "text/plain"
        }
        view.showAndroidSharesheet(Intent.createChooser(sendIntent, null))
    }

    override fun onTitleChangedResult(result: Bundle) {
        result.getString(Constants.TITLE_RESULT_KEY)?.let { title ->
            launch {
                qrModel.title = title
                repository.updateQrModel(qrModel)
                withContext(Dispatchers.Main) { view.setupToolbarTitle(title) }
            }
        }
    }
}
