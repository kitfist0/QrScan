package app.qrscan.ui.history.presenter

import android.os.Bundle
import app.qrscan.data.repository.QrModelRepository
import app.qrscan.ui.history.view.HistoryView
import kotlinx.coroutines.*

import kotlin.coroutines.CoroutineContext

class HistoryPresenterImpl(
    private val view: HistoryView,
    private val repository: QrModelRepository
): HistoryPresenter, CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.IO

    override fun onViewCreated(arguments: Bundle?) {
        launch {
            val qrModels = repository.getAllQrModels()
            withContext(Dispatchers.Main) { view.fillHistoryList(qrModels) }
        }
    }

    override fun onViewDestroyed() {
        coroutineContext.cancelChildren()
    }

    override fun onHistoryItemClicked(id: Long) {
        view.navigateToDetails(id)
    }

    override fun onItemFavButtonClicked(pos: Int, id: Long) {
        launch {
            val favorite = repository.updateAndGetFavorite(id)
            withContext(Dispatchers.Main) { view.changeItemFavoriteIcon(pos, favorite) }
        }
    }
}
