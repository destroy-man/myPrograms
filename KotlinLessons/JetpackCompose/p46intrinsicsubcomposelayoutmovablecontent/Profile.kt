package ru.korobeynikov.p46intrinsicsubcomposelayoutmovablecontent

data class Profile(
    val photo: String,
    val name: String,
    val birthday: String,
    val location: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val website: String? = null,
    val job: String? = null,
    val company: String? = null
)
