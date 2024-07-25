package com.example.databaseproject.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.databaseproject.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM TODO")
    fun getAllTodo():LiveData<List<Todo>>

    @Insert
    fun addToDo(todo: Todo)

    @Query("Delete FROM Todo where id = :id")
    fun deleteTodo(id:Int)


}