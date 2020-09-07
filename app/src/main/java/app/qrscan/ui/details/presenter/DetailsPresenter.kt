package app.qrscan.ui.details.presenter

import android.os.Bundle
import app.qrscan.data.db.entity.QrModel
import app.qrscan.ui.base.presenter.BasePresenter

interface DetailsPresenter: BasePresenter {

    var qrModel: QrModel

    fun onEditTitleClicked()

    fun onDeleteButtonClicked()

    fun onFavoriteButtonClicked()

    fun onShareButtonClicked()

    fun onCopyButtonClicked()

    fun onTitleChangedResult(result: Bundle)
}
