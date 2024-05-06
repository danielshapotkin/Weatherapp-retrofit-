import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import com.example.weather.domain.IImageDownloader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Url
import java.io.FileNotFoundException
import java.io.OutputStream

class ImageDownloader (private val context: Context) : IImageDownloader{
    interface ApiService {
        @GET
        suspend fun downloadImage(@Url url: String): ResponseBody
    }

    override suspend fun downloadImage(urlString: String?): Bitmap? {
        if (urlString.isNullOrEmpty()) return null

        val retrofit = Retrofit.Builder()
            .baseUrl("https://picsum.photos/")
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        return try {
            val response = apiService.downloadImage(urlString)
            val inputStream = response.byteStream()
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    override suspend fun saveImageToExternalStorage(bitmap: Bitmap) {
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
                try {
                    imageUri?.let {
                        val outputStream: OutputStream? = openOutputStream(imageUri)
                        outputStream?.use {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, it)
                        }
                    }
                }
                catch (e: FileNotFoundException){
                    e.printStackTrace()
                }


            }
        }
    }
}
