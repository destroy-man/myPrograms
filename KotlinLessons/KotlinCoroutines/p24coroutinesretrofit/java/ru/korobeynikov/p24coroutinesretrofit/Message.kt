package ru.korobeynikov.p24coroutinesretrofit

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("id")
    val id: Int,
    @SerializedName("time")
    val time: Long,
    @SerializedName("text")
    val text: String,
    @SerializedName("image")
    val image: String
)
