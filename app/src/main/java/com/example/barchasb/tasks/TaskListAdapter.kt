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
    val taskID: String, val taskTitle: String, val taskDescription: String, val type: TaskType
) : Parcelable

enum class TaskType {
    ASR, WORD_OCR
}


class TaskListAdapter(
    private val tasks: List<Task>,
    private val onTaskClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
        holder.itemView.setOnClickListener { onTaskClick(task) }
    }

    override fun getItemCount() = tasks.size

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idTextView: TextView = itemView.findViewById(R.id.taskID)
        private val titleTextView: TextView = itemView.findViewById(R.id.taskTitle)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.taskDescription)

        fun bind(task: Task) {
            idTextView.text = task.taskID
            titleTextView.text = task.taskTitle
            descriptionTextView.text = task.taskDescription
        }

        private fun getTaskType(type: TaskType): String {
            return when (type) {
                TaskType.ASR -> "ASR Task"
                TaskType.WORD_OCR -> "Word OCR Task"
            }
        }
    }
}
