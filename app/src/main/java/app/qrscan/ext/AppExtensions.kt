package app.qrscan.ext

import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.DrawableRes
import app.qrscan.R
import app.qrscan.data.db.QrModel
import app.qrscan.util.Constants
import com.google.zxing.*
import com.google.zxing.client.result.ParsedResult
import com.google.zxing.client.result.ParsedResultType
import com.google.zxing.client.result.ResultParser
import java.text.SimpleDateFormat
import java.util.*

internal fun EditText.customAfterTextChanged(action: (String)-> Unit) =
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun afterTextChanged(editable: Editable?) {
            editable?.let { action(it.toString()) }
        }
    })

internal fun ImageButton.setFavoriteIcon(favorite: Boolean) = setImageResource(
    when (favorite) {
        true -> R.drawable.ic_favorite
        false -> R.drawable.ic_favorite_border
    }
)

internal fun SharedPreferences.saveToGalleryEnabled() = getBoolean(Constants.PREF_SAVE_TO_GALLERY, false)

internal fun SharedPreferences.isVibrationEnabled() = getBoolean(Constants.PREF_VIBRATION, true)

internal fun SharedPreferences.isSoundEnabled() = getBoolean(Constants.PREF_SOUND, true)

internal fun Result.toQrModel(): QrModel {
    val time = System.currentTimeMillis()
    val title = SimpleDateFormat(Constants.DATE_FORMAT_24H, Locale.US).format(time)
    val parsedResult: ParsedResult? = try {
        ResultParser.parseResult(this)
    } catch (e: NotFoundException) {
        null
    }
    return QrModel(
            time = time,
            title = title,
            text = parsedResult?.displayResult ?: text,
            type = parsedResult?.type?.name ?: Constants.PARSED_RESULT_UNKNOWN,
            format = barcodeFormat.name
    )
}

@DrawableRes
internal fun QrModel.getDrawableRes(): Int = when (type) {
    ParsedResultType.EMAIL_ADDRESS.name -> R.drawable.ic_alternate_email
    ParsedResultType.ADDRESSBOOK.name -> R.drawable.ic_account
    ParsedResultType.CALENDAR.name -> R.drawable.ic_calendar
    ParsedResultType.PRODUCT.name -> R.drawable.ic_barcode
    ParsedResultType.ISBN.name -> R.drawable.ic_barcode
    ParsedResultType.TEXT.name -> R.drawable.ic_notes
    ParsedResultType.WIFI.name -> R.drawable.ic_wifi
    ParsedResultType.GEO.name -> R.drawable.ic_location
    ParsedResultType.TEL.name -> R.drawable.ic_phone
    ParsedResultType.URI.name -> R.drawable.ic_link
    ParsedResultType.SMS.name -> R.drawable.ic_sms
    ParsedResultType.VIN.name -> R.drawable.ic_car
    else -> R.drawable.ic_qr_code
}