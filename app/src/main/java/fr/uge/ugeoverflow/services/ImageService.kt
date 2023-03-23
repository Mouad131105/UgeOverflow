package fr.uge.ugeoverflow.services

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import fr.uge.ugeoverflow.session.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


object ImageService {


    fun saveImageToLocal(filename: String, imageUrl: String, context: Context) = runBlocking {
//        val filename= "$filename.png"
        val image = getImageFromServer(imageUrl)
        val bytes = image?.asAndroidBitmap()?.byteCount?.let { it1 -> ByteArray(it1) }

        val file = File(context.filesDir, "$filename.png")
        val outputStream = withContext(Dispatchers.IO) {
            FileOutputStream(file)
        }
        withContext(Dispatchers.IO) {
            outputStream.write(bytes)
        }
        outputStream.close()

    }


    fun loadImageFromLocal(filename: String, context: Context): ImageBitmap? = runBlocking {
        val file = File(context.filesDir, "$filename.png")
        if (!file.exists()) {
            return@runBlocking null
        }
        val inputStream = FileInputStream(file)
        val bytes = inputStream.readBytes()
        inputStream.close()
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        return@runBlocking bitmap?.asImageBitmap()
    }

    fun getImageFromServer(imageUrl: String): ImageBitmap? = runBlocking {
        var url = imageUrl
        if (url == "" || url == "null") {
            return@runBlocking null
        }
        if (url.contains("http")) {
            url = url.split("/images/")[1]
        }
        val res = ApiService.init().getImage(url)
        if (res.isSuccessful) {
            val image = res.body()?.bytes()
            if (image != null) {
                return@runBlocking BitmapFactory.decodeByteArray(image, 0, image.size)
                    .asImageBitmap()
            }
        }
        return@runBlocking null
    }
}
