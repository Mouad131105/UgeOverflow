package fr.uge.ugeoverflow.session

/**
 * implémenté par Achraf et Fares(Trex)
 */
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class SessionManager(context: Context ) {

    private val sharedPreferences = context.getSharedPreferences("SessionManager", Context.MODE_PRIVATE)
    private val _isAuthenticated = mutableStateOf(getToken() != null)
    val isUserLoggedIn: State<Boolean> = _isAuthenticated

    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun logIn(token : String) {
        _isAuthenticated.value = true
        sharedPreferences.edit().putString("token",token).apply()
    }

    fun logOut() {
        _isAuthenticated.value = false
        sharedPreferences.edit().clear().apply()
    }
}