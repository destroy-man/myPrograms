package ru.korobeynikov.viewmodelapplication.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.core.context.startKoin
import ru.korobeynikov.viewmodelapplication.di.homeViewModelModule

@Composable
@Preview
fun App() {
    startKoin {
        modules(homeViewModelModule)
    }
    Column(modifier = Modifier.safeContentPadding()) {
        HomeScreen()
    }
}