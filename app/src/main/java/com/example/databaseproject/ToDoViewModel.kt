package com.example.databaseproject

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.databaseproject.db.TodoDao
import com.example.databaseproject.db.TodoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ToDoViewModel:ViewModel() {
    val todoDao= MainApplication.todoDatabase.getToDoDao()
   // val todoList: LiveData<TodoDao> = todoDao
   // private val _selectedTodo : LiveData<List<Todo>> = todoDao.getAllTodo()
    private val todoDatabase: TodoDatabase = MainApplication.todoDatabase
    private val todoDao: TodoDao = todoDatabase.getToDoDao()
    private val _allTodos = MutableLiveData<List<Todo>>()
    val allTodos: LiveData<List<Todo>> = _allTodos

   //val selectedTodo : LiveData<List<Todo>> get() = _selectedTodo
  init {
      todoDao.observeForever { dao ->
          if (dao != null) {
              // Observe the getAllTodo() LiveData from the TodoDao

                  _allTodos.value =  dao.getAllTodo()

          }
      }
//    fun getAllToDo():LiveData<List<Todo>>{
//        return todoDao.getAllTodo()
//    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun addTodo(title:String){
        viewModelScope.launch(Dispatchers.IO){
            val nowDate = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val createdAt = formatter.format(nowDate)
            todoDao(Todo(title=title, createdAt = createdAt))
        }
    }
//    fun deleteTodo(id:Int){
//        viewModelScope.launch(Dispatchers.IO){
//            todoDao.deleteTodo(id)
//        }
//    }
//
//    fun getTodoById(id: Int):LiveData<Todo>{
//        return todoDao.getTodoById(id)
//
      }
    }

