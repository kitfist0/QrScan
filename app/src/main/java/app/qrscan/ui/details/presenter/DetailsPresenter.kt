package app.qrscan.ui.details.presenter

import app.qrscan.data.db.QrModel
import app.qrscan.ui.base.presenter.BasePresenter

interface DetailsPresenter: BasePresenter {

    var qrModel: QrModel

    fun onEditTitleClicked()

    fun onDeleteButtonClicked()

    fun onFavoriteButtonClicked()

    fun onShareButtonClicked()

    fun onCopyButtonClicked()
}
