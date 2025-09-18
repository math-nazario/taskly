package com.example.taskly.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.taskly.R
import com.example.taskly.database.AppDatabase
import com.example.taskly.databinding.ActivityTaskListBinding
import com.example.taskly.model.Task
import com.example.taskly.ui.adapter.TaskListAdapter

class TaskListActivity : AppCompatActivity() {
    private val adapter = TaskListAdapter(this)
    private lateinit var binding: ActivityTaskListBinding
    private val taskDao by lazy {
        AppDatabase.instance(this).taskDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configRecyclerView()
        configFab()
    }

    override fun onResume() {
        super.onResume()
        adapter.update(taskDao.getAll())
    }

    private fun configFab() {
        binding.fabAddTask.setOnClickListener {
            goToTaskActivity()
        }
    }

    private fun configRecyclerView() {
        binding.rvTasks.adapter = adapter
        adapter.clickItem = { task ->
            if (task.isCompleted) {
                Toast.makeText(
                    this,
                    getString(R.string.msg_task_completed_not_editable),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val intent = Intent(this, TaskActivity::class.java).apply {
                    putExtra(TASK_KEY_ID, task.id)
                }
                startActivity(intent)
            }
        }

        adapter.longClickItem = {
            confirmDelete(it)
        }

        adapter.onCheckChanged = { task, isChecked ->
            taskDao.update(task.copy(isCompleted = isChecked))
            adapter.update(taskDao.getAll())
        }
    }

    private fun goToTaskActivity() {
        val intent = Intent(this, TaskActivity::class.java)
        startActivity(intent)
    }

    private fun confirmDelete(task: Task) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.title_delete_task))
            .setMessage(getString(R.string.msg_delete_confirmation) + " \"${task.title}\"?")
            .setPositiveButton(getString(R.string.btn_yes)) { _, _ ->
                taskDao.delete(task)
                adapter.update(taskDao.getAll())
                Toast.makeText(this, getString(R.string.msg_task_deleted), Toast.LENGTH_SHORT)
                    .show()
            }
            .setNegativeButton(getString(R.string.btn_no), null)
            .show()
    }
}
