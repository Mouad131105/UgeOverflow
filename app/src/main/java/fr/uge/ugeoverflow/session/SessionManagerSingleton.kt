package fr.uge.ugeoverflow.session

import android.annotation.SuppressLint

object SessionManagerSingleton {
    @SuppressLint("StaticFieldLeak")
    lateinit var sessionManager: SessionManager
}
