package com.example.databaseproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.databaseproject.db.TodoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ToDoViewModel : ViewModel() {
    private val _allTodos = MutableLiveData<List<Todo>>()
    val allTodos: LiveData<List<Todo>> = _allTodos
    fun addTodo(title: String, dao: TodoDao) {
        viewModelScope.launch(Dispatchers.IO) {
            val nowDate = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val createdAt = formatter.format(nowDate)
            dao.addToDo(Todo(title = title, createdAt = createdAt))
        }
    }

}