package app.qrscan.ui.history.view

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import app.qrscan.data.db.QrModel
import app.qrscan.ui.history.HistoryAdapter
import app.qrscan.ui.history.HistoryFragmentDirections
import kotlinx.android.synthetic.main.fragment_history.view.*

class HistoryViewImpl(
    override var context: Context,
    override val navController: NavController
) : HistoryView {

    override lateinit var rootView: View

    override fun navigateToDetails(id: Long) {
        navController.navigate(HistoryFragmentDirections.fromHistoryToDetails(id))
    }

    override fun fillHistoryList(items: List<QrModel>) {
        (rootView.historyRecycler.adapter as HistoryAdapter).items = items
        rootView.emptyHistoryView.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun changeItemFavoriteIcon(pos: Int, favorite: Boolean) {
        val adapter = (rootView.historyRecycler.adapter as HistoryAdapter)
        adapter.items[pos].favorite = favorite
        adapter.notifyItemChanged(pos)
    }

    override fun showError(message: String) {
    }

    override fun showError(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
