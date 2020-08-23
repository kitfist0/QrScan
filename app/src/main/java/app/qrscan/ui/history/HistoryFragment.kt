package app.qrscan.ui.history

import androidx.recyclerview.widget.LinearLayoutManager
import app.qrscan.R
import app.qrscan.ui.base.BaseFragment
import app.qrscan.ui.history.presenter.HistoryPresenter
import app.qrscan.ui.history.view.HistoryView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_history.*

@AndroidEntryPoint
class HistoryFragment : BaseFragment<HistoryPresenter, HistoryView>(R.layout.fragment_history) {

    override fun setupViews() {
        super.setupViews()
        historyRecycler.layoutManager = LinearLayoutManager(requireContext())
        historyRecycler.adapter = HistoryAdapter(
            itemClickListener = { presenter.onHistoryItemClicked(it) },
            favButtonClickListener = { pos, id ->  presenter.onItemFavButtonClicked(pos, id) }
        )
    }
}
