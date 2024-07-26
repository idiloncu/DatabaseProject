package com.example.databaseproject

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class TodoCallback : DiffUtil.ItemCallback<ToDoModel>() {
    override fun areItemsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
        return oldItem.id==newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
        return oldItem.toString() == newItem.toString()
    }
}