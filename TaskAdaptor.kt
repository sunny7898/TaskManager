package com.example.taskmanager


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // Holds the reference to the objects inside the view
    var titleTextView : TextView = itemView.findViewById(R.id.taskNameTextView)

}

class TaskAdaptor(var context: Context, var tasks:ArrayList<String>) : RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.task_row_layout, null)
        var viewHolder = TaskViewHolder(view)
        return  viewHolder
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        // Receives the viewHolder from the oncreate, adds the data to the view.

        // get the item from the tasks
        var item = tasks[position]

        // display the task item on the text view
        holder.titleTextView.text = item
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

}