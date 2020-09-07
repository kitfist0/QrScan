package app.qrscan.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.qrscan.R
import app.qrscan.data.db.entity.QrModel
import app.qrscan.ext.getDrawableRes
import app.qrscan.ext.setFavoriteIcon
import app.qrscan.util.AutoUpdatableAdapter
import kotlinx.android.synthetic.main.item_history.view.*
import kotlin.properties.Delegates

class HistoryAdapter(
    private val itemClickListener: (id: Long) -> Unit,
    private val favButtonClickListener: (pos: Int, model: QrModel) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>(), AutoUpdatableAdapter {

    var items: List<QrModel> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o.id == n.id }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_history, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, items[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pos: Int, item: QrModel) = with(itemView) {
            itemImage.setImageResource(item.getDrawableRes())
            itemTitleText.text = item.title
            itemSubtitleText.text = item.text
            favoriteButton.setFavoriteIcon(item.favorite)
            favoriteButton.setOnClickListener { favButtonClickListener.invoke(pos, item) }
            setOnClickListener { itemClickListener.invoke(item.id) }
        }
    }
}
