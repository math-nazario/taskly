package com.example.taskly.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskly.databinding.TaskItemBinding
import com.example.taskly.model.Task

class TaskListAdapter(
    private val context: Context,
    tasks: List<Task> = emptyList(),
) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    private val tasks = tasks.toMutableList()

    inner class ViewHolder(private val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var task: Task

        fun bind(task: Task) {
            this.task = task
            binding.txtTitleTask.text = task.title
            binding.txtDescriptionTask.text = task.description
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = TaskItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskListAdapter.ViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = tasks.size

    fun update(all: List<Task>) {
        tasks.clear()
        tasks.addAll(all)
        notifyDataSetChanged()
    }
}
