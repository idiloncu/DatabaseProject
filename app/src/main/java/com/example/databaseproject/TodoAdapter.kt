package com.example.databaseproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(private val todoDataList:List<Todo>,private val onItemClicked:(ToDoHolder)->Unit) : ListAdapter<ToDoModel,TodoAdapter.ToDoHolder>(TodoCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder = ToDoHolder(parent) {
//yeniden bakılacak
        val binding = RecyclerView.inflate(LayoutInflater.from(parent.context),parent,false)
        return ToDoHolder(binding){
            onItemClicked(todoDataList.get())
        }
    }

    override fun getItemCount(): Int {
        return todoDataList.size
    }
    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        //val currentItem = todoDataList[position]
        holder.bind(model = getItem(position))

    }
    inner class ToDoHolder(private val binding:RecyclerView,
                           private val onItemClick:(Int)->Unit
    ):RecyclerView.ViewHolder(binding.rootView){
        fun bind(model: ToDoModel){
            with(binding){
                itemView.setOnClickListener{
                    onItemClick(adapterPosition)
                }
              //  val title : Text  = itemView.findViewById(R.id.recyclerView)
            }
        }
    }
}