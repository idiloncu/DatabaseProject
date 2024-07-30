package com.example.databaseproject

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databaseproject.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val todoViewModel : ToDoViewModel by viewModels()
    private lateinit var todoAdapter: TodoAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        val toDoViewModel = ViewModelProvider(this)[ToDoViewModel::class.java]
        enableEdgeToEdge()

        val newList = listOf(Todo(0,"ffff","111"))

        todoAdapter = TodoAdapter(newList){ todo->
            Toast.makeText(this,"Added ${todo.title}",Toast.LENGTH_LONG).show()
        }

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = todoAdapter
        }
        binding.recyclerView.adapter.apply {
            binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        }
        binding.button.setOnClickListener{
            val title = binding.editText.text.toString()
            if (title.isNotEmpty()) {
                todoViewModel.addTodo(title)

                binding.editText.text.clear()
            }
        }
        todoViewModel.addTodo("Example Task")
        lifecycleScope.launch {todoViewModel.getAllToDo().observe(this@MainActivity, Observer<List<Todo>> { todos ->
            val stringBuilder = StringBuilder()
            todoAdapter.notifyDataSetChanged()
            todoAdapter = TodoAdapter(todos){ todo->
                //Toast.makeText(this,"Added ${todo.title}",Toast.LENGTH_LONG).show()
            }
            todos.forEach { todo ->
                stringBuilder.append("${todo.title} + ${todo.createdAt}\n")
            }
                binding.editText.setText(stringBuilder.toString())
        })
    }
        lifecycleScope.launch { todoViewModel.allTodos.observe(this@MainActivity) {todos ->
            todoAdapter.currentList
        } }
        }

    private fun getTodoList(selectedItem : ToDoModel){
      todoAdapter.submitList(todoAdapter.currentList.map { newItem->
            if (newItem==selectedItem){
                ToDoModel(
                    id = newItem.id,
                    title = newItem.title,
                    createdAt = newItem.createdAt
                )
            }
            else{
                newItem
            }
        }.sortedBy { it.title })
    }
}
