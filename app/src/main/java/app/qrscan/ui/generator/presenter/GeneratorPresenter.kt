package app.qrscan.ui.generator.presenter

import android.graphics.Bitmap
import app.qrscan.ui.base.presenter.BasePresenter

interface GeneratorPresenter: BasePresenter {

    var generatedBitmap: Bitmap?

    fun onTextChanged(text: String)

    fun onSaveButtonClicked()
}
