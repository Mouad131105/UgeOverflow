package fr.uge.ugeoverflow.services

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import fr.uge.ugeoverflow.R
import fr.uge.ugeoverflow.session.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


object ImageService {


    fun saveImageToLocal(filename: String, imageUrl: String, context: Context) = runBlocking {
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

    @Composable
    fun RemoteImage(url: String, modifier: Modifier = Modifier) {
        val painter = // Customize the image request as needed (e.g. add headers, transformations, etc.)
            rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = url)
                    .apply(block = fun ImageRequest.Builder.() {
                        // Customize the image request as needed (e.g. add headers, transformations, etc.)
                        placeholder(R.drawable.user2)
                    }).build()
            )
        Image(
            painter = painter,
            contentDescription = "Remote image",
            modifier = modifier,
            contentScale = ContentScale.Crop,
            alpha = if (painter.state is AsyncImagePainter.State.Loading) 0.5f else 1.0f,
            colorFilter = if (painter.state is AsyncImagePainter.State.Error) ColorFilter.tint(Color.Red) else null
        )
    }
}
