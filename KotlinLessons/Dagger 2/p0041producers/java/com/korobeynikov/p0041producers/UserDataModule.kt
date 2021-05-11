package com.korobeynikov.p0041producers

import dagger.producers.Produced
import dagger.producers.ProducerModule
import dagger.producers.Produces
import java.io.IOException
import java.util.concurrent.ExecutionException

@ProducerModule(includes=[NetworkModule::class])
class UserDataModule(user:User){

    var mUser=user

    @Throws(IOException::class)
    @Produces
    fun getUserDataJson(networkUtils:NetworkUtils):String{
        return networkUtils.getUserDataJson(mUser)
    }

    @Produces
    fun getUserData(userDataJson:Produced<String>):UserData{
        try {
            return UserData.parseFromJson(userDataJson.get())
        }
        catch(ex:ExecutionException){
            return UserData.WRONG_USER
        }
    }
}