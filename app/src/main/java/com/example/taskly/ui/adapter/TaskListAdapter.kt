package com.example.taskly.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskly.R
import com.example.taskly.databinding.TaskItemBinding
import com.example.taskly.model.Task
import com.example.taskly.util.Priority
import java.text.SimpleDateFormat
import java.util.Locale

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}

class TaskListAdapter(
    private val context: Context,
    tasks: List<Task> = emptyList(),
    var clickItem: (task: Task) -> Unit = {},
    var longClickItem: (task: Task) -> Unit = {},
    var onCheckChanged: (task: Task, isChecked: Boolean) -> Unit = { _, _ -> },
    var onEditClick: (task: Task) -> Unit = {},
    var onDeleteClick: (task: Task) -> Unit = {}
) : ListAdapter<Task, TaskListAdapter.ViewHolder>(TaskDiffCallback()) {

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

            binding.btnMoreOptions.setOnClickListener {
                if (::task.isInitialized) {
                    val popup = PopupMenu(context, it)
                    popup.inflate(R.menu.menu_task_item)

                    popup.setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.mnEditTask -> {
                                onEditClick(task)
                                true
                            }

                            R.id.mnDeleteTask -> {
                                onDeleteClick(task)
                                true
                            }

                            else -> false
                        }
                    }
                    popup.show()
                }
            }
        }

        fun bind(task: Task) {
            this.task = task

            binding.cbCompletedTask.setOnCheckedChangeListener(null)
            binding.cbCompletedTask.isChecked = task.isCompleted
            binding.cbCompletedTask.setOnCheckedChangeListener { _, isChecked ->
                onCheckChanged(task, isChecked)
            }

            binding.txtTitleTask.paint.isStrikeThruText = task.isCompleted
            binding.txtTitleTask.text = task.title
            binding.txtDescriptionTask.text = task.description

            if (task.dueDate != null) {
                val formattedDate = SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()
                ).format(task.dueDate)

                binding.txtDueDateTask.text = formattedDate
                binding.txtDueDateTask.visibility = View.VISIBLE
            } else {
                binding.txtDueDateTask.visibility = View.GONE
            }

            binding.vwPriorityIndicator.setBackgroundColor(getPriorityColor(task.priority))

            binding.txtPriorityLabel.text = when (task.priority) {
                Priority.LOW -> context.getString(R.string.priority_low)
                Priority.MEDIUM -> context.getString(R.string.priority_medium)
                Priority.HIGH -> context.getString(R.string.priority_high)
            }

            binding.txtPriorityLabel.setTextColor(getPriorityColor(task.priority))
        }
    }

    private fun getPriorityColor(priority: Priority): Int {
        return when (priority) {
            Priority.LOW -> ContextCompat.getColor(context, android.R.color.holo_green_dark)
            Priority.MEDIUM -> ContextCompat.getColor(context, android.R.color.holo_orange_dark)
            Priority.HIGH -> ContextCompat.getColor(context, android.R.color.holo_red_dark)
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
        val task = getItem(position)
        holder.bind(task)
    }
}
