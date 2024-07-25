package com.example.databaseproject

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ToDoViewModel:ViewModel() {
    val todoDao= MainApplication.todoDatabase.getToDoDao()
    val todoList:LiveData<List<Todo>> = todoDao.getAllTodo()
    //gettoDo ekle
    val getTodo = MainApplication.todoDatabase.getToDoDao()

    @RequiresApi(Build.VERSION_CODES.O)
    fun addTodo(title:String){
        viewModelScope.launch(Dispatchers.IO){
            val nowDate = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val createdAt = formatter.format(nowDate)
            todoDao.addToDo(Todo(title=title, createdAt = createdAt))
        }
    }
    fun deleteTodo(id:Int){
        viewModelScope.launch(Dispatchers.IO){
            todoDao.deleteTodo(id)
        }
    }
}