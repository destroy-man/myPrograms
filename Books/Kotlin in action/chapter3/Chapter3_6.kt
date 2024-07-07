package ru.korobeynikov.chapter3

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//Чистим код: локальные функции и расширения
@Composable
fun Chapter3_6() {
    Text(text = " ${saveUser(User(1, "", ""))}")
}

fun User.validateBeforeSave() {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty())
            throw IllegalArgumentException("Can't save $id: empty $fieldName")
    }
    validate(name, "Name")
    validate(address, "Address")
}

fun saveUser(user: User): String {
    user.validateBeforeSave()
    return "Save user"
}