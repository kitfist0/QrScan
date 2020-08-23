package app.qrscan.ui.details

import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import app.qrscan.R
import app.qrscan.ui.base.BaseFragment
import app.qrscan.ui.details.presenter.DetailsPresenter
import app.qrscan.ui.details.view.DetailsView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*

@AndroidEntryPoint
class DetailsFragment: BaseFragment<DetailsPresenter, DetailsView>(R.layout.fragment_details),
        Toolbar.OnMenuItemClickListener, View.OnClickListener {

    override fun setupViews() {
        super.setupViews()

        favoriteButton.setOnClickListener(this)
        deleteButton.setOnClickListener(this)
        shareButton.setOnClickListener(this)
        copyFab.setOnClickListener(this)

        toolbar.setOnMenuItemClickListener(this)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.edit -> presenter.onEditTitleClicked().also { return true }
            else -> return false
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.favoriteButton -> presenter.onFavoriteButtonClicked()
            R.id.deleteButton -> presenter.onDeleteButtonClicked()
            R.id.shareButton -> presenter.onShareButtonClicked()
            R.id.copyFab -> presenter.onCopyButtonClicked()
        }
    }
}
