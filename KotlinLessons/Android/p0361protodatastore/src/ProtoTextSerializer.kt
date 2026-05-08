package ru.korobeynikov.p0361protodatastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.datastore.snippets.proto.ProtoText
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object ProtoTextSerializer : Serializer<ProtoText> {

    override val defaultValue: ProtoText = ProtoText.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ProtoText {
        try {
            return ProtoText.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: ProtoText, output: OutputStream) {
        return t.writeTo(output)
    }
}