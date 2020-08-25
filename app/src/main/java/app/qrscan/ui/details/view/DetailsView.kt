package app.qrscan.ui.details.view

import android.content.Intent
import android.graphics.Bitmap
import androidx.navigation.NavController
import app.qrscan.ui.base.view.BaseView

interface DetailsView: BaseView {

    val navController: NavController

    fun setupToolbarTitle(title: String)

    fun setupEncodedText(text: String)

    fun setupQrImage(bitmap: Bitmap?)

    fun setupFavoriteIcon(favorite: Boolean)

    fun navigateToEditTitleDialog(title: String)

    fun showAndroidSharesheet(shareIntent: Intent)

    fun popBackStack()
}
