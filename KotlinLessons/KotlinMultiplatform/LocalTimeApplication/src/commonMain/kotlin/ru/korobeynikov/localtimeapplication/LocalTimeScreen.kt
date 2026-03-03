package ru.korobeynikov.localtimeapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.IllegalTimeZoneException
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import localtimeapplication.composeapp.generated.resources.Res
import localtimeapplication.composeapp.generated.resources.egypt
import localtimeapplication.composeapp.generated.resources.france
import localtimeapplication.composeapp.generated.resources.indonesia
import localtimeapplication.composeapp.generated.resources.japan
import localtimeapplication.composeapp.generated.resources.mexico
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Clock

@Composable
fun LocalTimeScreen(countries: List<Country> = defaultCountries) {
    //LocalTimeScreen с использованием флагов для стран
    var showCountries by remember { mutableStateOf(false) }
    var timeAtLocation by remember { mutableStateOf("No location selected") }

    Column(modifier = Modifier.padding(20.dp).safeContentPadding().fillMaxSize()) {
        Text(
            timeAtLocation,
            style = TextStyle(fontSize = 20.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
        )
        Row(modifier = Modifier.padding(start = 20.dp, top = 10.dp)) {
            DropdownMenu(expanded = showCountries, onDismissRequest = {
                showCountries = false
            }) {
                countries.forEach { (name, zone, image) ->
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painterResource(image),
                                    modifier = Modifier.size(50.dp).padding(end = 10.dp),
                                    contentDescription = "$name flag"
                                )
                                Text(name)
                            }
                        },
                        onClick = {
                            timeAtLocation = currentTimeAt(name, zone)
                            showCountries = false
                        }
                    )
                }
            }
        }

        Button(modifier = Modifier.padding(start = 20.dp, top = 10.dp), onClick = {
            showCountries = !showCountries
        }) {
            Text("Select location")
        }
    }
}

data class Country(val name: String, val zone: TimeZone, val image: DrawableResource)

val defaultCountries = listOf(
    Country("Japan", TimeZone.of("Asia/Tokyo"), Res.drawable.japan),
    Country("France", TimeZone.of("Europe/Paris"), Res.drawable.france),
    Country("Mexico", TimeZone.of("America/Mexico_City"), Res.drawable.mexico),
    Country("Indonesia", TimeZone.of("Asia/Jakarta"), Res.drawable.indonesia),
    Country("Egypt", TimeZone.of("Africa/Cairo"), Res.drawable.egypt)
)

@Composable
fun LocalTimeScreenCountriesNames(countries: List<CountryWithoutImages> = countries()) {
    var showCountries by remember { mutableStateOf(false) }
    var timeAtLocation by remember { mutableStateOf("No location selected") }

    Column(modifier = Modifier.padding(20.dp).safeContentPadding().fillMaxSize()) {
        Text(
            timeAtLocation,
            style = TextStyle(fontSize = 20.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
        )
        Row(modifier = Modifier.padding(start = 20.dp, top = 10.dp)) {
            DropdownMenu(expanded = showCountries, onDismissRequest = {
                showCountries = false
            }) {
                countries.forEach { (name, zone) ->
                    DropdownMenuItem(
                        text = {
                            Text(name)
                        },
                        onClick = {
                            timeAtLocation = currentTimeAt(name, zone)
                            showCountries = false
                        }
                    )
                }
            }
        }

        Button(modifier = Modifier.padding(start = 20.dp, top = 10.dp), onClick = {
            showCountries = !showCountries
        }) {
            Text("Select location")
        }
    }
}

data class CountryWithoutImages(val name: String, val zone: TimeZone)

fun currentTimeAt(location: String, zone: TimeZone): String {
    fun LocalTime.formatted() = "$hour:$minute:$second"

    val time = Clock.System.now()
    val localTime = time.toLocalDateTime(zone).time

    return "The time in $location is ${localTime.formatted()}"
}

fun countries() = listOf(
    CountryWithoutImages("Japan", TimeZone.of("Asia/Tokyo")),
    CountryWithoutImages("France", TimeZone.of("Europe/Paris")),
    CountryWithoutImages("Mexico", TimeZone.of("America/Mexico_City")),
    CountryWithoutImages("Indonesia", TimeZone.of("Asia/Jakarta")),
    CountryWithoutImages("Egypt", TimeZone.of("Africa/Cairo"))
)

@Composable
fun LocalTimeScreenImprovedStyle() {
    var location by remember { mutableStateOf("Europe/Paris") }
    var timeAtLocation by remember { mutableStateOf("No location selected") }

    Column(modifier = Modifier.padding(20.dp).safeContentPadding().fillMaxSize()) {
        Text(
            timeAtLocation,
            style = TextStyle(fontSize = 20.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
        )
        TextField(
            value = location,
            modifier = Modifier.padding(top = 10.dp),
            onValueChange = {
                location = it
            }
        )
        Button(modifier = Modifier.padding(top = 10.dp), onClick = {
            timeAtLocation = currentTimeAt(location) ?: "Invalid location"
        }) {
            Text("Show Time")
        }
    }
}

@Composable
fun LocalTimeScreenSimpleStyle() {
    var location by remember { mutableStateOf("Europe/Paris") }
    var timeAtLocation by remember { mutableStateOf("No location selected") }
    Column(modifier = Modifier.safeContentPadding().fillMaxSize()) {
        Text(timeAtLocation)
        TextField(value = location, onValueChange = {
            location = it
        })
        Button(onClick = {
            timeAtLocation = currentTimeAt(location) ?: "Invalid location"
        }) {
            Text("Show Time At Location")
        }
    }
}

fun currentTimeAt(location: String): String? {
    fun LocalTime.formatted() = "$hour:$minute:$second"

    return try {
        val time = Clock.System.now()
        val zone = TimeZone.of(location)
        val localTime = time.toLocalDateTime(zone).time
        "The time in $location is ${localTime.formatted()}"
    } catch (ex: IllegalTimeZoneException) {
        null
    }
}

@Composable
fun LocalTimeScreenDefault() {
    var location by remember { mutableStateOf("Europe/Paris") }
    var timeAtLocation by remember { mutableStateOf("No location selected") }
    Column(modifier = Modifier.safeContentPadding().fillMaxSize()) {
        Text(timeAtLocation)
        TextField(value = location, onValueChange = {
            location = it
        })
        Button(onClick = {
            timeAtLocation = "13:30"
        }) {
            Text("Show Time At Location")
        }
    }
}