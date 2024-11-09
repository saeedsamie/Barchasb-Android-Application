package com.example.barchasb.tasks

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.barchasb.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    val taskID: String,
    val taskTitle: String,
    val taskDescription: String
) : Parcelable

class TaskListAdapter(
    private val tasks: List<Task>,
    private val onTaskClicked: (Task) -> Unit
) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val task_id = itemView.findViewById<TextView>(R.id.taskID)
        val title = itemView.findViewById<TextView>(R.id.taskTitle)
        val description = itemView.findViewById<TextView>(R.id.taskDescription)

        fun bind(task: Task) {
            task_id.text = task.taskID
            title.text = task.taskTitle
            description.text = task.taskDescription
            itemView.setOnClickListener { onTaskClicked(task) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size
}
