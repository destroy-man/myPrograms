package ru.korobeynikov.p22scaffolddrawersnackbarbottomsheet

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() = Scaffold(bottomBar = {
    NavigationBar {
        NavigationBarItem(selected = true, onClick = {}, icon = {
            Icon(Icons.Filled.Home, contentDescription = null)
        }, label = {
            Text(text = "Home")
        })
        NavigationBarItem(selected = false, onClick = {}, icon = {
            Icon(Icons.Filled.Call, contentDescription = null)
        }, label = {
            Text(text = "Call")
        })
    }
}) { _ ->
    Text(text = "Content")
}