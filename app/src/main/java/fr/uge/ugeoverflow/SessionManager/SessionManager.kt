package fr.uge.ugeoverflow.SessionManager

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class SessionManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("SessionManager", Context.MODE_PRIVATE)

    private val _isUserLoggedIn = mutableStateOf(sharedPreferences.getBoolean("isUserLoggedIn", false))
    val isUserLoggedIn: State<Boolean> = _isUserLoggedIn

    fun logIn() {
        _isUserLoggedIn.value = true
        sharedPreferences.edit().putBoolean("isUserLoggedIn", true).apply()
    }

    fun logOut() {
        _isUserLoggedIn.value = false
        sharedPreferences.edit().putBoolean("isUserLoggedIn", false).apply()
    }
}