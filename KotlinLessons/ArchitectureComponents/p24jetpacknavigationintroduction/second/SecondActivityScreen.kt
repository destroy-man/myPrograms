package ru.korobeynikov.p24jetpacknavigationintroduction.second

sealed interface SecondActivityScreen {
    data object Screen4 : SecondActivityScreen
    data object Screen5 : SecondActivityScreen
}