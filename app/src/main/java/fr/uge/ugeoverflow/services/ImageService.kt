package fr.uge.ugeoverflow.services

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File

object ImageService {

    suspend fun saveUserImageToLocal(userId: String, imageUrl: String, context: Context) {

        val filename = "user_$userId.jpg"

        // Load the image using Coil image loading library
        val imageBitmap = CoilImageLoader(context).loadImageAsBitmap(imageUrl)
        val localFilePath = context.filesDir.absolutePath + filename

        // Save the image to local storage
        val file = File(localFilePath)
        if (!file.exists()) {
            val outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            withContext(Dispatchers.IO) {
                outputStream.close()
            }
        }
    }

    fun loadUserImageFromLocal(userId: String, context: Context): BitmapDrawable?= runBlocking {
        val filename = "user_$userId.jpg"
        val file = File(context.filesDir.absolutePath + filename)
        if (!file.exists()) {
//            return@runBlocking null
//            saveUserImageToLocal(userId, , context)
        }
        return@runBlocking ImageLoader(context).execute(
            ImageRequest.Builder(context)
                .data(file)
                .build()
        ).drawable as BitmapDrawable
    }

    class CoilImageLoader(private val context: Context) {
        private val imageLoader = ImageLoader.Builder(context)
            .build()

        suspend fun loadImageAsBitmap(imageUrl: String): Bitmap {
            val request = ImageRequest.Builder(context)
                .data(imageUrl)
                .build()
            val result = imageLoader.execute(request)
            return (result.drawable as BitmapDrawable).bitmap
        }

    }
}