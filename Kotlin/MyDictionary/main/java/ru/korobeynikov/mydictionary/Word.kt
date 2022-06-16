package ru.korobeynikov.mydictionary

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId

open class Word(
    @PrimaryKey
    var id: String = "",
    var original: String = "",
    var translation: String = ""
): RealmObject()