package com.example.databaseproject

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        mContext=applicationContext
    }
    companion object{
        var mContext : Context? = null

    }
}