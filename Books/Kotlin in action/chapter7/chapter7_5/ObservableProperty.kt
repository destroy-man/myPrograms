package ru.korobeynikov.chapter7.chapter7_5

import java.beans.PropertyChangeSupport
import kotlin.reflect.KProperty

class ObservableProperty(
    private var propValue: Int,
    private val changeSupport: PropertyChangeSupport,
) {

    operator fun getValue(p: Person7_5_3, prop: KProperty<*>): Int = propValue

    operator fun setValue(p: Person7_5_3, prop: KProperty<*>, newValue: Int) {
        val oldValue = propValue
        propValue = newValue
        changeSupport.firePropertyChange(prop.name, oldValue, newValue)
    }
}