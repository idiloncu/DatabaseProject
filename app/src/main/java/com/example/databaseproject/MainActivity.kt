package com.example.databaseproject

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.databaseproject.databinding.ActivityMainBinding
import com.example.databaseproject.db.TodoDao
import com.example.databaseproject.db.TodoDatabase
import com.example.databaseproject.db.TodoDatabase.Companion.NAME
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val todoViewModel: ToDoViewModel by viewModels()
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var todoDatabase: TodoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        todoAdapter = TodoAdapter()

        var database = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            NAME
        ).build()

        val dao: TodoDao = database.getToDoDao()
        binding.recyclerView.adapter = todoAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        submitTodo()
        binding.button.setOnClickListener {
            val title = binding.editText.text.toString()
            if (title.isNotEmpty()) {
                todoViewModel.addTodo("title",  dao)
                var todoList = mutableListOf<TodoDatabase>()
                //  todoList.add(0, title, "111")
                todoAdapter.submitList(todoAdapter.currentList.map { newItem ->
                    ToDoModel(
                        id = newItem.id,
                        title = newItem.title,
                        createdAt = newItem.createdAt
                    )
                })
                binding.editText.text.clear()
                //eklenilen rw listesi tek itemli, eklemem gereken liste db den gelecek
            }
        }
        lifecycleScope.launch {
            todoViewModel.allTodos.observe(this@MainActivity) { todos ->
                todoAdapter.currentList
            }
        }
    }
//    private fun getTodoList(selectedItem: ToDoModel) {
//        todoAdapter.submitList(todoAdapter.currentList.map { newItem ->
//            if (newItem == selectedItem) {
//                ToDoModel(
//                    id = newItem.id,
//                    title = newItem.title,
//                    createdAt = newItem.createdAt
//                )
//            } else {
//                newItem
//            }
//        }.sortedBy { it.title })
//    }
    fun submitTodo() {
        lifecycleScope.launch {
            todoViewModel.allTodos.observe(this@MainActivity, Observer { todos ->
                val todoList = mutableListOf<ToDoModel>()
                todos.forEach { todo ->
                    todoList.add(ToDoModel(todo.id, todo.title, todo.createdAt))
                }
                todoAdapter.submitList(todoList)

            })
        }
    }
}
