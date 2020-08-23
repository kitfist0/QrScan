package app.qrscan.ui.generator.presenter

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import app.qrscan.R
import app.qrscan.data.repository.QrModelRepository
import app.qrscan.ui.generator.view.GeneratorView
import app.qrscan.util.Constants
import app.qrscan.util.EncodeUtil
import com.google.zxing.NotFoundException
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class GeneratorPresenterImpl(
    private val view: GeneratorView,
    private val encodeUtil: EncodeUtil,
    private val repository: QrModelRepository
): GeneratorPresenter, CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.IO

    override var generatedBitmap: Bitmap? = null

    override fun onViewCreated(arguments: Bundle?) {
        view.setupDefaultText()
    }

    override fun onViewDestroyed() {
        coroutineContext.cancelChildren()
    }

    override fun onTextChanged(text: String) {
        when {
            text.isEmpty() -> view.setupEmptyQrImage().also { generatedBitmap = null }
            text.length > Constants.INPUT_TEXT_MAX_LENGTH -> view.showError(R.string.text_is_too_long)
            else -> launch {
                encodeUtil.textToQrCodeBitmap(text).also { generatedBitmap = it }
                withContext(Dispatchers.Main) { view.setupQrImage(generatedBitmap) }
            }
        }
    }

    override fun onSaveButtonClicked() {
        generatedBitmap?.let { bitmap ->
            launch {
                try {
                    val model = encodeUtil.bitmapToModel(bitmap)
                    val id = repository.insertQrModel(model)
                    withContext(Dispatchers.Main) {
                        view.setupDefaultText()
                        view.navigateToDetails(id)
                    }
                } catch (e: NotFoundException) {
                    // stackoverflow.com/questions/10583622
                    withContext(Dispatchers.Main) {
                        val message = e.javaClass.simpleName + ": " + e.message
                        Log.e(GeneratorPresenterImpl::class.java.simpleName, message)
                        view.showError(R.string.text_is_too_long)
                    }
                }
            }
        } ?: apply {
            view.showError(R.string.empty_text_field)
        }
    }
}
