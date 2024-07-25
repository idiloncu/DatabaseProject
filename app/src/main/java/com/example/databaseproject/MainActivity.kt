package com.example.databaseproject

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.databaseproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val todoViewModel : ToDoViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        val toDoViewModel = ViewModelProvider(this)[ToDoViewModel::class.java]
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.button.setOnClickListener{
            val title = binding.editTextText.text.toString()
            if (title.isNotEmpty()) {
                todoViewModel.addTodo(title)
                    binding.editTextText.text.clear()
            }
        }
        todoViewModel.addTodo("Example Task")
        todoViewModel.todoList.observe(this, Observer<List<Todo>> { todos ->
            val stringBuilder = StringBuilder()
            todos.forEach { todo ->
                stringBuilder.append("${todo.title} - ${todo.createdAt}\n")
            }
                binding.editTextText.setText(stringBuilder.toString())
        })
    }

}
