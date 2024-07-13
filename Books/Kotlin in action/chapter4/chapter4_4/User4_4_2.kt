package ru.korobeynikov.chapter4.chapter4_4

class User4_4_2 private constructor(val nickname: String) {
    companion object {

        fun newSubscribingUser(email: String) = User4_4_2(email.substringBefore('@'))

        fun newFacebookUser(accountId: Int) = User4_4_2(getFacebookName(accountId))

        private fun getFacebookName(facebookAccountId: Int) = "Facebook"
    }
}