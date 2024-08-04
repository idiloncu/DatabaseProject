package com.example.databaseproject.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.databaseproject.model.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM TODO")
    fun getAllTodo():List<Todo>

    @Insert
    fun addToDo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM todo WHERE id = :id ")
    fun getTodoById(id: Int):LiveData<Todo>

}