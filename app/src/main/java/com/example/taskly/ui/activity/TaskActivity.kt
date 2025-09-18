package com.example.taskly.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import com.example.taskly.util.Priority
import androidx.appcompat.app.AppCompatActivity
import com.example.taskly.R
import com.example.taskly.database.AppDatabase
import com.example.taskly.databinding.ActivityTaskBinding
import com.example.taskly.model.Task
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskBinding
    private var taskId = 0L
    private val taskDao by lazy {
        AppDatabase.instance(this).taskDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.title_register_task)
        confSaveButton()
        tryToLoadTask()

        binding.edtDueDateTask.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, year, month, day ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, month, day)

                val timestamp = selectedCalendar.timeInMillis
                binding.edtDueDateTask.setText(
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(timestamp))
                )
                binding.edtDueDateTask.tag = timestamp
            }, year, month, day).show()
        }
    }

    override fun onResume() {
        super.onResume()
        taskDao.getById(taskId)?.let {
            fillFields(it)
        }
    }

    private fun tryToLoadTask() {
        taskId = intent.getLongExtra(TASK_KEY_ID, 0L)
    }

    private fun confSaveButton() {
        binding.btnSaveTask.setOnClickListener {
            val task = registerTask()
            if (task != null) {
                taskDao.add(task)
                finish()
            }
        }
    }

    private fun registerTask(): Task? {
        val title = binding.tieTitleTask.text.toString()
        val description = binding.tieDescriptionTask.text.toString()
        val dueDate = binding.edtDueDateTask.tag as? Long
        val selectedIndex = binding.spnPriorityTask.selectedItemPosition
        val priority = Priority.entries[selectedIndex]

        if (title.isBlank()) {
            Toast.makeText(this, getString(R.string.msg_title_required), Toast.LENGTH_SHORT).show()
            return null
        }

        return Task(
            id = taskId,
            title = title,
            description = description,
            isCompleted = false,
            dueDate = dueDate,
            priority = priority
        )
    }

    private fun fillFields(task: Task) {
        title = getString(R.string.title_edit_task)
        with(binding) {
            tieTitleTask.setText(task.title)
            tieDescriptionTask.setText(task.description)
            task.dueDate?.let {
                edtDueDateTask.setText(
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it))
                )
                edtDueDateTask.tag = it
            }
            spnPriorityTask.setSelection(task.priority.ordinal)
        }
    }
}
