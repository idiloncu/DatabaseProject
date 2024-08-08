package com.example.databaseproject

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.example.databaseproject.model.ToDoModel
import com.example.databaseproject.model.Todo
import com.example.databaseproject.viewmodel.ToDoViewModel
import com.google.android.material.snackbar.Snackbar
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
        save(view)
        if (binding.editText.text == binding.recyclerView.context){
            delete(view)
        }

        todoAdapter = TodoAdapter(dao.getAllTodo())
        binding.recyclerView.adapter = todoAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)


        lifecycleScope.launch {
            todoViewModel.allTodos.observe(this@MainActivity) { todos ->
                todoAdapter.todoList
            }
        }
        binding.deleteButton.setOnClickListener {
            delete(view)
        }

    }
    fun save(view: View){

        binding.saveButton.setOnClickListener{
            val title = binding.editText.text.toString()
            if (title.isNotEmpty()) {
                todoViewModel.addTodo(binding.editText.text.toString(), dao)
                binding.editText.text.clear()
                Toast.makeText(this, "Todo is successfully Added", Toast.LENGTH_LONG).show()
            }
        }

    }
    fun delete(view: View){
        val title = binding.editText.text.toString()
        val todoDelete=todoAdapter.todoList.find { it.title == title }
        todoDelete?.let {
            todoViewModel.deleteTodo(it, dao)
            todoAdapter.notifyDataSetChanged()
            binding.editText.text.clear()
            Toast.makeText(this, "Todo is Deleted", Toast.LENGTH_LONG).show()
        }
    }

}
