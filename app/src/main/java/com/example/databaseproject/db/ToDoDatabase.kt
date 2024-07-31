package com.example.databaseproject.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.databaseproject.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase:RoomDatabase() {
    companion object{
        const val NAME = "Todo_DB"
    }
    abstract fun getToDoDao():TodoDao
}