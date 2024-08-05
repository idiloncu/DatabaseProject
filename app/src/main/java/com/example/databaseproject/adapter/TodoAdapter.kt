package com.example.databaseproject.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.databaseproject.databinding.RecyclerRowBinding
import com.example.databaseproject.model.Todo
import com.example.databaseproject.model.ToDoModel

class TodoAdapter(var todoList: List<Todo>) : RecyclerView.Adapter<TodoAdapter.ToDoHolder>() {
    class ToDoHolder(val recyclerRowBinding: RecyclerRowBinding) : RecyclerView.ViewHolder(recyclerRowBinding.root) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
        val recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoHolder(recyclerRowBinding)
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
    fun submitList(list: List<Todo>) {
        todoList = list
        notifyDataSetChanged()
    }

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