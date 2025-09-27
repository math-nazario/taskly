package com.example.taskly.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taskly.R
import com.example.taskly.database.AppDatabase
import com.example.taskly.databinding.ActivityTaskBinding
import com.example.taskly.extensions.TasklyDatePicker
import com.example.taskly.model.Task
import com.example.taskly.util.DateUtils
import com.example.taskly.util.Priority
import com.example.taskly.util.TASK_KEY_ID

class TaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding
    private var taskId = 0L
    private var selectedDate: Long? = null
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
            val datePicker = TasklyDatePicker.futureOnly(getString(R.string.hint_due_date))
            datePicker.addOnPositiveButtonClickListener { selection ->
                selection?.let {
                    selectedDate = it
                    binding.edtDueDateTask.setText(DateUtils.format(it))
                }
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER")
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
        val dueDate = selectedDate
        val selectedIndex = binding.spnPriorityTask.selectedItemPosition
        val priority = Priority.entries[selectedIndex]

        if (title.isBlank()) {
            Toast.makeText(
                this,
                getString(R.string.msg_title_required),
                Toast.LENGTH_SHORT
            ).show()
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
                edtDueDateTask.setText(DateUtils.format(it))
                selectedDate = it
            }

            spnPriorityTask.setSelection(task.priority.ordinal)
        }
    }
}
