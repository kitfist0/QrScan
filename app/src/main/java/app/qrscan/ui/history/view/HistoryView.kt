package app.qrscan.ui.history.view

import androidx.navigation.NavController
import app.qrscan.data.db.entity.QrModel
import app.qrscan.ui.base.view.BaseView

interface HistoryView: BaseView {

    val navController: NavController

    fun fillHistoryList(items: List<QrModel>)

    fun changeItemFavoriteIcon(pos: Int, favorite: Boolean)

    fun navigateToDetails(id: Long)
}
