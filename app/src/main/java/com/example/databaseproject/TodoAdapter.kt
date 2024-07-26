package com.example.databaseproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class TodoAdapter(private val todoDataList:List<Todo>) : RecyclerView.Adapter<ToDoHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder = ToDoHolder(parent) {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_main,parent,false)
        return ToDoHolder(itemView)
    }

    override fun getItemCount(): Int {
        return todoDataList.size
    }
    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        //val currentItem = todoDataList[position]
        holder.bi

    }
    inner class ToDoHolder(private val binding:RecyclerView):RecyclerView.ViewHolder(binding.rootView){
        fun bind(model: ToDoModel){
            with(binding){
                val title : Text  = itemView.findViewById(R.id.recyclerView)
            }
        }
    }
}