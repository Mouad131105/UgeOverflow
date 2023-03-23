package fr.uge.ugeoverflow.session

/**
 * implémenté par Achraf et Fares
 */
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import fr.uge.ugeoverflow.api.UserBoxDTO

class SessionManager(val context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("SessionManager", Context.MODE_PRIVATE)
    private val _isAuthenticated = mutableStateOf(getToken() != null)
    val isUserLoggedIn: State<Boolean> = _isAuthenticated
    private val _username = mutableStateOf(getUsername())
    private val _image = mutableStateOf(getImage())


    fun getImage(): String? {
        return sharedPreferences.getString("imagename", null)
    }

    fun getUsername(): String? {
        return sharedPreferences.getString("username", null)
    }

    val imageName: State<String?>
        get() = _image

    val currentUsername: State<String?>
        get() = _username

    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun logIn(token: String, user: UserBoxDTO) {
        _isAuthenticated.value = true
        _username.value = user.username
        val image = if (user.profilePicture.contains("http")) user.profilePicture.split("/images/")[1] else user.profilePicture
        _image.value = image
        sharedPreferences.edit()
            .putString("token", token)
            .putString("username", user.username)
            .putString("imagename", image)
            .apply()
    }


    fun logOut() {
        _isAuthenticated.value = false
        _username.value = null
        _image.value = null
        sharedPreferences.edit().clear().apply()
    }

}