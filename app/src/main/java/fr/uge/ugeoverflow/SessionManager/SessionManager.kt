package fr.uge.ugeoverflow.SessionManager

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import fr.uge.ugeoverflow.model.Address
import fr.uge.ugeoverflow.model.User

class SessionManager(context: Context ) {

    private val sharedPreferences = context.getSharedPreferences("SessionManager", Context.MODE_PRIVATE)
    private val _isUserLoggedIn = mutableStateOf(sharedPreferences.getBoolean("isUserLoggedIn", false))
    private val _user = mutableStateOf<User?>(null)//recupere l'utilisateur facilement
    val isUserLoggedIn: State<Boolean> = _isUserLoggedIn
    val user: State<User?> = _user

    fun getUserfromSharedPref() : User? {
        val user = User(
            sharedPreferences.getInt("id", -1),
            sharedPreferences.getString("firstName" , "")!!,
            sharedPreferences.getString("lastName" ,"")!!,
            sharedPreferences.getString("username" ,"")!!,
            sharedPreferences.getString("email" ,"")!!,
            sharedPreferences.getString("address" ,"")!!
        )
        _user.value = user
        return  user
    }

    fun logIn(user: User) {
        _isUserLoggedIn.value = true
        _user.value = user
        sharedPreferences.edit().putInt("id", user.id).apply()
        sharedPreferences.edit().putBoolean("isUserLoggedIn", true).apply()
        sharedPreferences.edit().putString("firstName",user.firstName).apply()
        sharedPreferences.edit().putString("lastName",user.lastName).apply()
        sharedPreferences.edit().putString("username",user.username).apply()
        sharedPreferences.edit().putString("email" ,user.email).apply()
        sharedPreferences.edit().putString("address",user.address.toString()).apply()
        sharedPreferences.edit().putString("token","4567798").apply()
    }

    fun logOut() {
        _isUserLoggedIn.value = false
        _user.value = null
        sharedPreferences.edit().clear().apply()
        sharedPreferences.edit().putBoolean("isUserLoggedIn", false).apply()
    }
}