package dev.bulean.notwallet

import android.app.Application
import android.util.Log

class NotWalletApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.i("TAG", "Init notWallet app")
    }

}