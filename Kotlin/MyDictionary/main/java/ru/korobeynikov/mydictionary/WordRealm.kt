package ru.korobeynikov.mydictionary

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class WordRealm(
    @PrimaryKey
    var id: String = "",
    var original: String = "",
    var translation: String = ""
) : RealmObject()