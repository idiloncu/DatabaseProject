package com.example.databaseproject.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.databaseproject.databinding.RecyclerRowBinding
import com.example.databaseproject.model.Todo
import com.example.databaseproject.model.ToDoModel

class TodoAdapter(var todoList: List<Todo>,private val onItemClicked:(ToDoModel)->Unit) : ListAdapter<ToDoModel, TodoAdapter.ToDoHolder>(TodoCallback()) {
    class ToDoHolder(val recyclerRowBinding: RecyclerRowBinding,private val onItemClicked: (Int) -> Unit)
        : RecyclerView.ViewHolder(recyclerRowBinding.root) {
        fun bind(todoModel: ToDoModel) {
            with(recyclerRowBinding){
                    itemView.setOnClickListener {
                        onItemClicked(adapterPosition)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
        val recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoHolder(recyclerRowBinding, onItemClicked = { position ->
            val todo = todoList[position]
            recyclerRowBinding.recyclerViewTextView.text = todo.title
        })
    }
    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        holder.recyclerRowBinding.recyclerViewTextView.text=todoList.get(position).title
        holder.itemView.setOnClickListener {
            Log.v("test", "item clicked")
        }
    }
    override fun getItemCount(): Int {
        return todoList.size
    }
//    fun submitList(list: List<Todo>) {
//        todoList = list
//        notifyDataSetChanged()
//    }


    class TodoCallback : DiffUtil.ItemCallback<ToDoModel>() {
        override fun areItemsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
            return oldItem.id == newItem.id
        }
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
            return oldItem == newItem
        }
    }
}