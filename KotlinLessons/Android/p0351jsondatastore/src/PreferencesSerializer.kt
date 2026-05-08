package ru.korobeynikov.p0351jsondatastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object JsonTextSerializer : Serializer<JsonText> {

    override val defaultValue: JsonText = JsonText("")

    override suspend fun readFrom(input: InputStream): JsonText {
        return try {
            Json.decodeFromString<JsonText>(input.readBytes().decodeToString())
        } catch (exception: SerializationException) {
            throw CorruptionException("Unable to read Preferences", exception)
        }
    }

    override suspend fun writeTo(t: JsonText, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(Json.encodeToString(t).encodeToByteArray())
        }
    }
}