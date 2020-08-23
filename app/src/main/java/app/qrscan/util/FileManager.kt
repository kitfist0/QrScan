package app.qrscan.util

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FileManager(private val context: Context) {

    companion object {
        private const val DATE_FORMAT = "dd.MM.yyy_HH:mm:ss"
        private const val QR_FOLDER_NAME = "QRcode"

        private fun createFileName(): String {
            val timeStamp = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(Date())
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(Date())
            return "${QR_FOLDER_NAME}_$timeStamp.jpg"
        }
    }

    fun saveBitmapToGallery(bitmap: Bitmap) {
        var compressedBitmap: Bitmap? = null
        if (bitmap.height > Constants.IMAGE_SIZE || bitmap.width > Constants.IMAGE_SIZE) {
            try {
                compressedBitmap = Glide.with(context)
                        .asBitmap()
                        .load(this)
                        .apply(RequestOptions().override(Constants.IMAGE_SIZE))
                        .submit()
                        .get()
            } catch (ignore: Exception) {
            }
        }
        val stream = ByteArrayOutputStream()
        compressedBitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val bytes = stream.toByteArray()
        takeIf { bytes.isNotEmpty() }?.saveToGallery(bytes)
    }

    private fun saveToGallery(image: ByteArray) {
        val savePath = arrayOf<File?>()
        val name = createFileName()
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val sdCard = context.getExternalFilesDir(null)
            val dir = File( "${sdCard?.absolutePath}/$QR_FOLDER_NAME")
            try {
                savePath[0] = saveBytesToFile(dir, name, image);
            } catch (ignore: IOException) {
            }
        }
    }

    @Throws(IOException::class)
    private fun saveBytesToFile(dir: File, name: String, image: ByteArray): File? {
        var path: File?
        val file = File(dir, name)
        FileOutputStream(file).use { f ->
            path = file
            MediaScannerConnection.scanFile(context, arrayOf(file.absolutePath), null, null)
            f.write(image)
            f.flush()
        }
        return path
    }
}
