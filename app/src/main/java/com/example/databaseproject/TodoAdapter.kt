package com.example.databaseproject

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.databaseproject.databinding.RecyclerRowBinding

class TodoAdapter() :
    ListAdapter<ToDoModel,
            TodoAdapter.ToDoHolder>(TodoCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoHolder(binding) { position ->
            onItemClicked(getItem(position))
        }
    }

    fun onItemClicked(item: ToDoModel) {

    }
    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        Log.d("log", "onBindViewHolder: ")
        holder.bind(currentList[position])
    }

    inner class ToDoHolder(
        private val binding: RecyclerRowBinding,
        private val onItemClick: (Int) -> (Unit)
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(model: ToDoModel) {
            with(binding) {
                binding.recyclerViewTextView.text = model.title

            }
        }
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