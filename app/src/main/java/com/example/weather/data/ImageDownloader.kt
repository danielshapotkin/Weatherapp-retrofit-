import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class ImageDownloader(private val context: Context, private val imageView: ImageView) {

    suspend fun downloadImage(urlString: String?): Bitmap? {
        var bitmap: Bitmap? = null

        withContext(Dispatchers.IO) {
            try {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val inputStream = connection.inputStream
                bitmap = BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return bitmap
    }

    suspend fun displayImage(bitmap: Bitmap?) {
        bitmap?.let {
            imageView.setImageBitmap(it)
            saveImageToExternalStorage(it)
        }
    }

    private suspend fun saveImageToExternalStorage(bitmap: Bitmap) {
        withContext(Dispatchers.IO) {
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "iiiimage.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.WIDTH, bitmap.width)
                put(MediaStore.Images.Media.HEIGHT, bitmap.height)
            }

            val resolver = context.contentResolver

            resolver.run {
                val imageUri = insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                imageUri?.let {
                    val outputStream: OutputStream? = imageUri.let { uri ->
                        openOutputStream(uri)
                    }
                    outputStream?.use { out ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                    }
                }
            }
        }
    }
}
