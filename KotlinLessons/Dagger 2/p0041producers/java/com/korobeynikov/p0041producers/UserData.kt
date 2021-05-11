package com.korobeynikov.p0041producers

class UserData {
    companion object{
        val WRONG_USER=UserData()
        fun parseFromJson(userDataJson:String):UserData{
            return UserData()
        }
    }
}