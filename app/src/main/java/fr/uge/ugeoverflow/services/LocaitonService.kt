package fr.uge.ugeoverflow.services

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Bundle
import android.os.IBinder
import androidx.core.app.ActivityCompat
import fr.uge.ugeoverflow.session.ApiService
import fr.uge.ugeoverflow.session.SessionManagerSingleton
import kotlinx.coroutines.runBlocking

class LocationService : Service() {

    private lateinit var locationManager: LocationManager

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            // Handle location updates here
            saveLocation(location.latitude, location.longitude)
        }

        override fun onProviderEnabled(provider: String) {}

        override fun onProviderDisabled(provider: String) {}

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0f,
            locationListener
        )
        return START_STICKY
    }

    private fun saveLocation(latitude: Double, longitude: Double) {

    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager.removeUpdates(locationListener)
    }

    companion object {
        fun getLocation(context: Context): fr.uge.ugeoverflow.model.Location? {
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO:
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    1234 // ??
                )
                null
            } else {
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }

            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            return if (location != null) {
                fr.uge.ugeoverflow.model.Location(location.latitude, location.longitude)
            } else {
                null
            }
        }
    }
}
