package app.qrscan.ui.base.view

import android.content.Context
import android.view.View

interface BaseView {

    var rootView: View

    var context: Context

    fun showError(message: String)

    fun showError(message: Int)

    fun showMessage(message: Int)
}
