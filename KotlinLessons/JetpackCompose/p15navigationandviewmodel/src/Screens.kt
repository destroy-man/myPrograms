package ru.korobeynikov.p15navigationandviewmodel

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

private const val TAG = "myLogs"

@Composable
fun UsersScreen(
    navController: NavController,
    countUsers: Int,
    viewModel: UserViewModel = hiltViewModel(),
) {
    val usersIdList = List(countUsers) { "${it + 1}" }
    LazyColumn {
        item {
            Text(text = "Users screen")
        }
        items(count = countUsers) { id ->
            Text(
                "user ${usersIdList[id]}",
                modifier = Modifier.clickable { navController.navigate("user/${id + 1}") }
            )
        }
    }
}

@Composable
fun UserScreen(id: String?, userViewModel: UserViewModel = hiltViewModel()) {
    Text(text = "User $id")
    userViewModel.showUserId()
    Log.d(TAG, "viewModel ${userViewModel.hashCode().toHexString()}")
}

@Composable
fun UsersScreenVMWithoutParameters(
    navController: NavController,
    countUsers: Int,
    viewModel: UserViewModel = viewModel(),
) {
    val usersIdList = List(countUsers) { "${it + 1}" }
    LazyColumn {
        item {
            Text(text = "Users screen")
        }
        items(count = countUsers) { id ->
            Text(
                "user ${usersIdList[id]}",
                modifier = Modifier.clickable { navController.navigate("user/${id + 1}") }
            )
        }
    }
}

@Composable
fun UsersScreenDifferentViewModels(navController: NavController, countUsers: Int) {
    val usersIdList = List(countUsers) { "${it + 1}" }
    LazyColumn {
        item {
            Text(text = "Users screen")
        }
        items(count = countUsers) { id ->
            Text(
                "user ${usersIdList[id]}",
                modifier = Modifier.clickable { navController.navigate("user/${id + 1}") }
            )
        }
    }
}

@Composable
fun UsersScreenCustomNavigation(onUser1Click: () -> Unit, onUser2Click: () -> Unit) {
    Column {
        Text(text = "Users screen")
        Text(text = "User 1", modifier = Modifier.clickable(onClick = onUser1Click))
        Text(text = "User 2", modifier = Modifier.clickable(onClick = onUser2Click))
    }
}

@Composable
fun UserScreenVMWithoutParameters(id: String?, userViewModel: UserViewModel = viewModel()) {
    Text(text = "User $id")
    Log.d(TAG, "user $id")
    Log.d(TAG, "viewModel ${userViewModel.hashCode().toHexString()}")
}