package ru.korobeynikov.ch08templatemethod.withhook

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BeverageScreen(
    teaAnswer: String,
    coffeeAnswer: String,
    onChangeTeaAnswer: (String) -> Unit,
    onChangeCoffeeAnswer: (String) -> Unit
) {
    //Паттерн Шаблонный метод
    Column {
        val teaHook = TeaWithHook()
        val coffeeHook = CoffeeWithHook()

        Text("\nMaking tea...")
        Text("Would you like lemon with your tea (y/n)?")
        OutlinedTextField(value = teaAnswer, onValueChange = onChangeTeaAnswer)
        Text(teaHook.prepareRecipe(teaAnswer))

        Text("\nMaking coffee...")
        Text("Would you like milk and sugar with your coffee (y/n)?")
        OutlinedTextField(value = coffeeAnswer, onValueChange = onChangeCoffeeAnswer)
        Text(coffeeHook.prepareRecipe(coffeeAnswer))
    }
}