package ru.korobeynikov.chapter4.chapter4_2

class FacebookUser(accountId: Int) : User4_2_3 {

    override val nickname = getFacebookName(accountId)

    private fun getFacebookName(accountId: Int) = "Facebook"
}