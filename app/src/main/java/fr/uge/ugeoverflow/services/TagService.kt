package fr.uge.ugeoverflow.services

import android.util.Log
import fr.uge.ugeoverflow.session.ApiService
import kotlinx.coroutines.runBlocking

object TagService {

    fun getTags(): List<String> = runBlocking {
        val re = ApiService.init().getTags()
        val tags: List<String> = if (re.isSuccessful) {
            re.body() ?: listOf()
        } else listOf()
        Log.e("TAGS", tags.toString())
        tags
    }
}
