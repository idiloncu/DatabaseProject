package com.example.databaseproject

import android.app.Application
import androidx.room.Room
import com.example.databaseproject.db.TodoDatabase
import com.example.databaseproject.db.TodoDatabase.Companion.NAME

class MainApplication : Application() {
    companion object {
        lateinit var todoDatabase : TodoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        todoDatabase =Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            NAME
        ).build()
    }

}