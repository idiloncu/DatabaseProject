package com.example.databaseproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.databaseproject.MyApplication.Companion.mContext
import com.example.databaseproject.db.TodoDao
import com.example.databaseproject.db.TodoDatabase
import com.example.databaseproject.db.TodoDatabase.Companion.NAME
import com.example.databaseproject.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ToDoViewModel : ViewModel() {
    private val _allTodos = MutableLiveData<List<Todo>>()
    val allTodos: LiveData<List<Todo>> = _allTodos


    init {
        mContext?.let {
            db = Room.databaseBuilder(it, TodoDatabase::class.java, NAME)
                .allowMainThreadQueries()    // it worked but I should use Rxjava for huge apps
                .build()
            dao = db?.getToDoDao()
            dao?.let { dao -> updateTodo(dao) }
        }
    }

    fun addTodo(title: String, dao: TodoDao) {
        viewModelScope.launch(Dispatchers.IO) {
            val nowDate = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val createdAt = formatter.format(nowDate)
            dao.addToDo(Todo(title = title, createdAt = createdAt))
            updateTodo(dao)
        }
    }

    fun deleteTodo(todo: Todo, dao: TodoDao) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteTodo(todo)
            updateTodo(dao)
        }
    }

    fun updateTodo(dao: TodoDao) {
        viewModelScope.launch(Dispatchers.IO) {
            _allTodos.postValue(dao.getAllTodo())
        }
    }

    companion object {
        var db: TodoDatabase? = null
        var dao: TodoDao? = null


    }
}