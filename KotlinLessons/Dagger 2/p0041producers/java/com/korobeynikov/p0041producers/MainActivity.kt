package com.korobeynikov.p0041producers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user=User()
        val userComponent=DaggerUserComponent.builder().userDataModule(UserDataModule(user)).build()
        val listenableFutureUserData=userComponent.getUserData()
        Futures.addCallback(listenableFutureUserData,object:FutureCallback<UserData>{
            override fun onSuccess(result: UserData?) {

            }

            override fun onFailure(t: Throwable) {

            }
        })
    }
}