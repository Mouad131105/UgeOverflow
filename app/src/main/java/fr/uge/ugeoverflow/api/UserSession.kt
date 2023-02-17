package fr.uge.ugeoverflow.api

import android.content.Context

object UserSession {
    // use getSharedPrefences instead
    private var token: String? = null
    private var isAuthenticated:Boolean = false
    private var username:String? = null


    fun setUserSession(username:String, newToken: String) {
        token = newToken
        isAuthenticated = true
        this.username = username
    }

    fun getToken(): String? {
        return token
    }
    fun isAuthenticated() : Boolean {
        return isAuthenticated;
    }
    fun getUsername(): String? {
        return username;
    }
}