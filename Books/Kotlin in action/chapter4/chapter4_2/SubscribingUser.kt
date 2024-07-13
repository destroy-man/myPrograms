package ru.korobeynikov.chapter4.chapter4_2

class SubscribingUser(private val email: String) : User4_2_3 {
    override val nickname: String
        get() = email.substringBefore('@')
}