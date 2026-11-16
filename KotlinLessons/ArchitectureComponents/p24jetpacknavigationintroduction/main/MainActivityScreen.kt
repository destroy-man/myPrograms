package ru.korobeynikov.p24jetpacknavigationintroduction.main

sealed interface MainActivityScreen {
    data object Screen1 : MainActivityScreen
    data object Screen2 : MainActivityScreen
    data object Screen3 : MainActivityScreen
    data object SecondActivity : MainActivityScreen
}