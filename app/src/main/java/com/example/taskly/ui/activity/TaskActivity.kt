package com.example.taskly.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taskly.database.AppDatabase
import com.example.taskly.databinding.ActivityTaskBinding
import com.example.taskly.model.Task

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
        title = "Register Task"
        confSaveButton()
        tryToLoadTask()
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
        if (title.isBlank() || description.isBlank()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            return null
        }

        return Task(
            id = taskId,
            title = title,
            description = description,
            isCompleted = false
        )
    }

    private fun fillFields(task: Task) {
        title = "Edit Task"
        with(binding) {
            tieTitleTask.setText(task.title)
            tieDescriptionTask.setText(task.description)
        }
    }
}
