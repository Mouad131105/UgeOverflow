package fr.uge.ugeoverflow.session

/**
 * implémenté par Achraf et Fares
 */
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class SessionManager(val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("SessionManager", Context.MODE_PRIVATE)
    private val _isAuthenticated = mutableStateOf(getToken() != null)
    val isUserLoggedIn: State<Boolean> = _isAuthenticated
    private val _username = mutableStateOf(getUsername())

    fun getUsername(): String? {
        return sharedPreferences.getString("username", null)
    }

    val currentUsername: State<String?>
        get() = _username

    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun logIn(token: String, username: String) {
        _isAuthenticated.value = true
        _username.value = username
        sharedPreferences.edit()
            .putString("token", token)
            .putString("username", username)
            .apply()
    }


    fun logOut() {
        _isAuthenticated.value = false
        _username.value = null
        sharedPreferences.edit().clear().apply()
    }

}