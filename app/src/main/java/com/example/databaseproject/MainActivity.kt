package com.example.databaseproject

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.databaseproject.adapter.TodoAdapter
import com.example.databaseproject.databinding.ActivityMainBinding
import com.example.databaseproject.db.TodoDao
import com.example.databaseproject.db.TodoDatabase
import com.example.databaseproject.db.TodoDatabase.Companion.NAME
import com.example.databaseproject.model.Todo
import com.example.databaseproject.viewmodel.ToDoViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val todoViewModel: ToDoViewModel by viewModels()
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var db: TodoDatabase
    private lateinit var dao: TodoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = Room.databaseBuilder(applicationContext, TodoDatabase::class.java, NAME)
            .allowMainThreadQueries()    // it worked but I should use Rxjava for huge apps
            .build()
        dao = db.getToDoDao()

        todoAdapter = TodoAdapter(dao.getAllTodo())

        binding.recyclerView.adapter = todoAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        val todo = Todo(binding.editText.text.toString(), binding.editText.text.toString())
        binding.button.setOnClickListener {
            val title = binding.editText.text.toString()
            if (title.isNotEmpty()) {
                todoViewModel.addTodo(binding.editText.text.toString(), dao)
                binding.editText.text.clear()
            }
        }
        lifecycleScope.launch {
            todoViewModel.allTodos.observe(this@MainActivity) { todos ->
                todoAdapter.todoList
            }
        }
    }
}
