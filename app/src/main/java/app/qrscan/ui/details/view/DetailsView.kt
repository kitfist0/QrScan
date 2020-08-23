package app.qrscan.ui.details.view

import android.graphics.Bitmap
import androidx.navigation.NavController
import app.qrscan.ui.base.view.BaseView

interface DetailsView: BaseView {

    val navController: NavController

    fun setupToolbarTitle(title: String)

    fun setupEncodedText(text: String)

    fun setupQrImage(bitmap: Bitmap?)

    fun setupFavoriteIcon(favorite: Boolean)

    fun popBackStack()
}
