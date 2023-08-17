package ru.korobeynikov.p11rxjava2

class Generator {
    fun setCallback(callback: GeneratorCallback?) {
        for (i in 1..20)
            callback?.generate(i)
    }
}