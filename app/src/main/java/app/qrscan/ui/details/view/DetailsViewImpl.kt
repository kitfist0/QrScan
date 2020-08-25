package app.qrscan.ui.details.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import app.qrscan.ext.setFavoriteIcon
import app.qrscan.ui.details.DetailsFragmentDirections
import kotlinx.android.synthetic.main.fragment_details.view.*

class DetailsViewImpl(
    override var context: Context,
    override val navController: NavController
) : DetailsView {

    override lateinit var rootView: View

    override fun setupToolbarTitle(title: String) {
        rootView.toolbar.title = title
    }

    override fun setupEncodedText(text: String) {
        rootView.encodedText.text = text
    }

    override fun setupQrImage(bitmap: Bitmap?) {
        rootView.qrCodeImage.setImageBitmap(bitmap)
    }

    override fun setupFavoriteIcon(favorite: Boolean) {
        rootView.favoriteButton.setFavoriteIcon(favorite)
    }

    override fun navigateToEditTitleDialog(title: String) {
        navController.navigate(DetailsFragmentDirections.fromDetailsToEditTitle(title))
    }

    override fun showAndroidSharesheet(shareIntent: Intent) {
        context.startActivity(shareIntent)
    }

    override fun popBackStack() {
        navController.popBackStack()
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showError(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
