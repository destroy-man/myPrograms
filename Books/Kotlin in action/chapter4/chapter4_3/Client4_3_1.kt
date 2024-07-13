package ru.korobeynikov.chapter4.chapter4_3

class Client4_3_1(val name: String, val postalCode: Int) {

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Client4_3_1)
            return false
        return name == other.name && postalCode == other.postalCode
    }

    override fun toString() = "Client(name=$name, postalCode=$postalCode)"

    override fun hashCode(): Int = name.hashCode() * 31 + postalCode

    fun copy(name: String = this.name, postalCode: Int = this.postalCode) =
        Client4_3_1(name, postalCode)
}