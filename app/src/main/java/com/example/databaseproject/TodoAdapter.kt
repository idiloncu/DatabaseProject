package com.example.databaseproject

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.databaseproject.databinding.RecyclerRowBinding
import org.w3c.dom.Text

class TodoAdapter(private var todoList: List<Todo>, private val onItemClicked: (Todo) -> Unit) : ListAdapter<ToDoModel,
        TodoAdapter.ToDoHolder>(TodoCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ToDoHolder(binding){ position->
            onItemClicked(getItem(position))
        }
    }
    fun onItemClicked(item:ToDoModel){

    }

//    override fun getItemCount(): Int {
//        return currentList.size
//    }
    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class ToDoHolder(private val binding:RecyclerRowBinding,
                           private val onItemClick:(Int)->(Unit)
    ):RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener{
                onItemClick(adapterPosition)
            }
        }
        fun bind(model: ToDoModel){
            with(binding){
                binding.recyclerViewTextView.text=model.title

            }
        }
    }
}
class TodoCallback : DiffUtil.ItemCallback<ToDoModel>(){
    override fun areItemsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
       return oldItem == newItem
    }
}