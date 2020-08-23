package app.qrscan.util

import android.graphics.Bitmap
import app.qrscan.data.db.QrModel
import app.qrscan.ext.toQrModel
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.util.*

class EncodeUtil(
    private val reader: MultiFormatReader,
    private val encoder: BarcodeEncoder
) {

    companion object {
        // https://stackoverflow.com/a/28851068/13865919
        private val hints = Hashtable<EncodeHintType, String>().apply {
            put(EncodeHintType.CHARACTER_SET, "utf-8")
        }
    }

    fun bitmapToModel(bitmap: Bitmap): QrModel {
        val intArray = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(intArray, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val source = RGBLuminanceSource(bitmap.width, bitmap.height, intArray)
        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
        return reader.decode(binaryBitmap).toQrModel()
    }

    fun textToQrCodeBitmap(text: String): Bitmap? = encodeBitmap(text, BarcodeFormat.QR_CODE)

    fun modelToBitmap(model: QrModel): Bitmap? {
        val format = BarcodeFormat.values().find { it.name == model.format } ?: BarcodeFormat.QR_CODE
        return encodeBitmap(model.text, format)
    }

    private fun encodeBitmap(text: String, format: BarcodeFormat): Bitmap? =
            try {
                encoder.encodeBitmap(text, format, 256, 256, hints)
            } catch (e: WriterException) {
                null
            }
}
