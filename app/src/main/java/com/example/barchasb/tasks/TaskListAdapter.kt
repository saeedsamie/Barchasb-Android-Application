package com.example.barchasb.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.barchasb.R
import com.example.barchasb.api.Task

class TaskListAdapter(
    private var tasks: List<Task>
) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.taskID)
        val titleTextView: TextView = itemView.findViewById(R.id.taskTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.taskDescription)
//        val taskTags: TextView = itemView.findViewById(R.id.task_tags)

        fun bind(task: Task) {
            idTextView.text = task.id.toString()
            titleTextView.text = task.title
            descriptionTextView.text = task.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
        holder.idTextView.text = task.id.toString()
        holder.titleTextView.text = task.title
        holder.descriptionTextView.text = task.description
//        holder.taskTags.text = task.tags
//        holder.itemView.setOnClickListener { onTaskClick(task) }
    }

    override fun getItemCount() = tasks.size

    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }

}
