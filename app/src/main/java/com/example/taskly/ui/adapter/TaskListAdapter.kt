package com.example.taskly.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.taskly.databinding.TaskItemBinding
import com.example.taskly.util.Priority
import com.example.taskly.model.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TaskListAdapter(
    private val context: Context,
    tasks: List<Task> = emptyList(),
    var clickItem: (task: Task) -> Unit = {},
    var longClickItem: (task: Task) -> Unit = {},
    var onCheckChanged: (task: Task, isChecked: Boolean) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    private val tasks = tasks.toMutableList()

    inner class ViewHolder(private val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var task: Task

        init {
            itemView.setOnClickListener {
                if (::task.isInitialized) {
                    clickItem(task)
                }
            }

            itemView.setOnLongClickListener {
                if (::task.isInitialized) {
                    longClickItem(task)
                }
                true
            }
        }

        fun bind(task: Task) {
            this.task = task
            binding.txtTitleTask.text = task.title
            binding.txtDescriptionTask.text = task.description
            binding.cbCompletedTask.isChecked = task.isCompleted

            binding.txtTitleTask.paint.isStrikeThruText = task.isCompleted

            binding.cbCompletedTask.setOnCheckedChangeListener { _, isChecked ->
                onCheckChanged(task, isChecked)
            }

            if (task.dueDate != null) {
                val formattedDate =
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(task.dueDate)
                binding.txtDueDateTask.text = "$formattedDate"
                binding.txtDueDateTask.visibility = View.VISIBLE
            } else {
                binding.txtDueDateTask.visibility = View.GONE
            }

            binding.vwPriorityIndicator.setBackgroundColor(
                when (task.priority) {
                    Priority.LOW -> ContextCompat.getColor(
                        context,
                        android.R.color.holo_green_dark
                    )

                    Priority.MEDIUM -> ContextCompat.getColor(
                        context,
                        android.R.color.holo_orange_dark
                    )

                    Priority.HIGH -> ContextCompat.getColor(
                        context,
                        android.R.color.holo_red_dark
                    )
                }
            )
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
