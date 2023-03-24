package fr.uge.ugeoverflow.services


import android.Manifest
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import fr.uge.ugeoverflow.R
import fr.uge.ugeoverflow.api.AddressDTO
import fr.uge.ugeoverflow.api.UserBoxDTO
import fr.uge.ugeoverflow.model.MyLocation
import fr.uge.ugeoverflow.session.ApiService
import java.util.*


fun getMyLocation(context: Context): MyLocation? {

    val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val criteria = Criteria()
    val provider = lm.getBestProvider(criteria, false)
    var location: Location? = null
    try {
        location = lm.getLastKnownLocation(provider!!)
    } catch (e: SecurityException) {
        Log.e("LocationService", "SecurityException")
    }
    return if (location != null) {
        MyLocation(location.latitude, location.longitude)
    } else {
        null
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Preview(showBackground = true)
@Composable
fun getCurrentLocation(context: Context = LocalContext.current): MyLocation? {

    val permissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    var currentMyLocation: Location? by remember { mutableStateOf(null) }

    if (!permissions.allPermissionsGranted) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Please grant location permission to use this feature")
            Button(
                onClick = {
                    permissions.launchMultiplePermissionRequest()
                }
            ) {
                Text("Grant permission")
            }
        }
    }

    DisposableEffect(Unit) {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val locationListener = object : LocationListener {

            override fun onLocationChanged(location: Location) {
                currentMyLocation = location
            }

            override fun onStatusChanged(
                provider: String?,
                status: Int,
                extras: Bundle?
            ) {
            }

            override fun onProviderEnabled(provider: String) {}

            override fun onProviderDisabled(provider: String) {}
        }

        try {
            val criteria = Criteria().apply {
                accuracy = Criteria.ACCURACY_FINE
            }
            val provider = lm.getBestProvider(criteria, true)
            if (provider != null) {
                lm.requestLocationUpdates(
                    provider,
                    10000L,
                    10.0f,
                    locationListener,
                    Looper.getMainLooper()
                )
                val lastKnownLocation = lm.getLastKnownLocation(provider)
                if (lastKnownLocation != null) {
                    currentMyLocation = lastKnownLocation
                    Log.d("Location", "Last known location: $lastKnownLocation")
                }
            }
        } catch (e: SecurityException) {
            // TODO: Handle permission denied exception
        }


        onDispose {
            lm.removeUpdates(locationListener)
        }
    }


    return currentMyLocation?.let {
        MyLocation(it.latitude, it.longitude)
    }
}


@Preview(showBackground = true)
@Composable
fun MyPopup() {
    // Define a state to track whether the popup is shown or not
    val showDialog = remember { mutableStateOf(false) }

    // Define the content of the popup
    val dialogContent = @Composable {
        Column {
            Text(text = "This is a popup!")
            Button(onClick = { showDialog.value = false }) {
                Text(text = "OK")
            }
        }
    }

    // Show the popup when the button is clicked
    Button(onClick = { showDialog.value = true }) {
        Text(text = "Show popup")
    }

    // Show the AlertDialog if showDialog is true
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Popup Title") },
            text = dialogContent,
            confirmButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text(text = "OK")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserBoxCardPopUp(
    user: UserBoxDTO = UserBoxDTO(
        UUID.randomUUID(),
        "ezig",
        "zeg",
        "http://localhost:8080/images/SCR-20230307-wis.png",
        address = AddressDTO(
            street = "123 Main St",
            city = "New York",
            country = "USA",
            zipCode = "10001"
        )
    ),
    content: @Composable () -> Unit = {
//        MyButton(onClick = { /*TODO*/ }, componentType = ComponentTypes.SecondaryOutline) {
        Text(text = "Follow")
//        }
    }
) {

    val showPopup = remember { mutableStateOf(false) }
    val imageByteArray = remember { mutableStateOf<ByteArray?>(null) }

    LaunchedEffect(user.profilePicture) {
        val byteArray = ApiService.init().getImage(user.profilePicture.split("/images/")[1])
        imageByteArray.value = byteArray.body()?.bytes()
        Log.i("IMAGE", imageByteArray.value.toString())
    }
    // Define the content of the popup
    val dialogContent = @Composable {
//        MyCard(
//            cardType = ComponentTypes.SecondaryOutline,
//            body = {

        Column {

            //RemoteImage(url = user.profilePicture)
            Image(
                painter = // default image
                rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = imageByteArray.value)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                            placeholder(R.drawable.user2) // default image
                        }).build()
                ),
                contentDescription = "User Image",
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colors.primary, CircleShape)
            )
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = user.username,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.body1
                )
            }

        }
//            }
//        )
    }

    // Show the popup when the button is clicked
    Column(
        Modifier
            .padding(10.dp)
            .clickable(onClick = { showPopup.value = true })
    ) {
        content()
    }

    // Show the DropdownMenu if showDialog is true
    if (showPopup.value) {
        DropdownMenu(
            expanded = showPopup.value,
            onDismissRequest = { showPopup.value = false },
            modifier = Modifier.width(IntrinsicSize.Max)
        ) {
            DropdownMenuItem(onClick = { showPopup.value = false }) {
                dialogContent()
            }
        }
    }
}
