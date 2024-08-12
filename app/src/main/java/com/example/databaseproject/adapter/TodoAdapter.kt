package com.example.databaseproject.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.databaseproject.databinding.RecyclerRowBinding
import com.example.databaseproject.model.Todo

class TodoAdapter : ListAdapter<Todo, TodoAdapter.ToDoHolder>(TodoCallback()) {
    class ToDoHolder(val recyclerRowBinding: RecyclerRowBinding,
                     private val onItemClicked: (Int) -> Unit)
        : RecyclerView.ViewHolder(recyclerRowBinding.root) {
        fun bind(todoModel: Todo) {
            with(recyclerRowBinding){
                recyclerViewTextView.text=todoModel.title
                    itemView.setOnClickListener {
                        onItemClicked(adapterPosition)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
        val recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoHolder(recyclerRowBinding, onItemClicked = { position ->
            val todo = currentList[position]
            recyclerRowBinding.recyclerViewTextView.text = todo.title
        })
    }
    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        holder.bind(currentList.get(position))
    }

    class TodoCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }
}