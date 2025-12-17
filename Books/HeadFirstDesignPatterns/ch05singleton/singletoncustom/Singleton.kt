package ru.korobeynikov.ch05singleton.singletoncustom

class Singleton private constructor() {
    companion object {

        @Volatile
        private lateinit var uniqueInstance: Singleton

        fun getInstance(): Singleton {
            if (!::uniqueInstance.isInitialized) {
                synchronized(Singleton::class) {
                    if (!::uniqueInstance.isInitialized) {
                        uniqueInstance = Singleton()
                    }
                }
            }
            return uniqueInstance
        }
    }
}