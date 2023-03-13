package fr.uge.ugeoverflow.SessionManager

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import fr.uge.ugeoverflow.model.Address
import fr.uge.ugeoverflow.model.User

class SessionManager(context: Context ) {

    private val sharedPreferences = context.getSharedPreferences("SessionManager", Context.MODE_PRIVATE)
    private val _isUserLoggedIn = mutableStateOf(sharedPreferences.getBoolean("isUserLoggedIn", false))
    val isUserLoggedIn: State<Boolean> = _isUserLoggedIn

    fun getTokenrfromSharedPref() : String? {
        val token = sharedPreferences.getString("token", null)
        return token
    }

    fun logIn(token : String) {
        _isUserLoggedIn.value = true
        sharedPreferences.edit().putString("token",token).apply()
    }

    fun logOut() {
        _isUserLoggedIn.value = false
        sharedPreferences.edit().clear().apply()
        sharedPreferences.edit().putBoolean("isUserLoggedIn", false).apply()
    }
}