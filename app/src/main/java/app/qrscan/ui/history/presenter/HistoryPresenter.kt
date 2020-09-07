package app.qrscan.ui.history.presenter

import app.qrscan.data.db.entity.QrModel
import app.qrscan.ui.base.presenter.BasePresenter

interface HistoryPresenter: BasePresenter {

    fun onHistoryItemClicked(id: Long)

    fun onItemFavButtonClicked(pos: Int, model: QrModel)
}
