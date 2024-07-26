package com.example.databaseproject

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.databaseproject.db.TodoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ToDoViewModel:ViewModel() {
    val todoDao= MainApplication.todoDatabase.getToDoDao()
    val todoList:LiveData<List<Todo>> = todoDao.getAllTodo()
    val getTodo : LiveData<List<Todo>> = todoDao.getAllTodo()
    //gettoDo ekle
    private val _selectedTodo : LiveData<List<Todo>> = todoDao.getAllTodo()
    val selectedTodo : LiveData<List<Todo>> get() = _selectedTodo

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

    fun getTodoById(id: Int):LiveData<Todo>{
        return todoDao.getTodoById(id)

        }
    }
