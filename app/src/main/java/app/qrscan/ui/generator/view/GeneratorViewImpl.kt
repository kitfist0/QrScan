package app.qrscan.ui.generator.view

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import app.qrscan.R
import app.qrscan.ui.generator.GeneratorFragmentDirections
import kotlinx.android.synthetic.main.fragment_generator.view.*

class GeneratorViewImpl(
    override var context: Context,
    override val navController: NavController
) : GeneratorView {

    override lateinit var rootView: View

    override fun setupDefaultText() {
        rootView.textInputEditText.setText(R.string.generate_default_text)
    }

    override fun setupQrImage(bitmap: Bitmap?) {
        rootView.generatedQrCodeImage.setImageBitmap(bitmap)
    }

    override fun setupEmptyQrImage() {
        rootView.generatedQrCodeImage.setImageResource(R.drawable.rectangle)
    }

    override fun navigateToDetails(id: Long) {
        navController.navigate(GeneratorFragmentDirections.fromGeneratorToDetails(id))
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
