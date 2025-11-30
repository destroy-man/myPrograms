package ru.korobeynikov.ch04factory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.korobeynikov.ch04factory.abstractfactory.PizzaStoreScreenAbstractFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PizzaStoreScreenAbstractFactory()
        }
    }
}