package ru.korobeynikov.p15navigationandviewmodel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun UserListScreen(
    onUserClick: () -> Unit,
    usersSharedViewModel: UsersSharedViewModel = viewModel(),
) =
    Column {
        Text(text = "Users screen")
        Text(text = "User", modifier = Modifier.clickable(onClick = onUserClick))
    }

@Composable
fun UserScreen(usersSharedViewModel: UsersSharedViewModel = viewModel()) = Text(text = "User")