package com.example.taskly.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.taskly.R
import com.example.taskly.database.AppDatabase
import com.example.taskly.databinding.ActivityTaskListBinding
import com.example.taskly.model.Task
import com.example.taskly.ui.adapter.TaskListAdapter
import com.example.taskly.util.TASK_KEY_ID
import com.google.android.material.snackbar.Snackbar

class TaskListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskListBinding
    private val adapter = TaskListAdapter(this)
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
        updateTaskList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_task_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnSortDate -> {
                updateTaskList(taskDao.sortByDate())
            }

            R.id.mnSortPriority -> {
                updateTaskList(taskDao.sortByPriority())
            }

            R.id.mnFilterAll -> {
                updateTaskList()
            }

            R.id.mnFilterPending -> {
                updateTaskList(taskDao.getPending())
            }

            R.id.mnFilterCompleted -> {
                updateTaskList(taskDao.getCompleted())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configFab() {
        binding.fabAddTask.setOnClickListener {
            goToTaskActivity()
        }
    }

    private fun configRecyclerView() {
        binding.rvTasks.adapter = adapter
        adapter.onEditClick = { task ->
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
        adapter.onDeleteClick = {
            confirmDelete(it)
        }

        adapter.onCheckChanged = { task, isChecked ->
            taskDao.update(task.copy(isCompleted = isChecked))
            updateTaskList()
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
                updateTaskList()
                Snackbar.make(
                    binding.root,
                    getString(R.string.msg_task_deleted),
                    Snackbar.LENGTH_LONG
                ).setAction(getString(R.string.btn_undo)) {
                    taskDao.add(task)
                    updateTaskList()
                }.show()
            }
            .setNegativeButton(getString(R.string.btn_no), null)
            .show()
    }

    private fun updateTaskList(tasks: List<Task>? = null) {
        val tasks = tasks ?: taskDao.getAll()
        adapter.submitList(tasks)

        if (tasks.isEmpty()) {
            binding.rvTasks.visibility = View.GONE
            binding.emptyStateContainer.visibility = View.VISIBLE
        } else {
            binding.rvTasks.visibility = View.VISIBLE
            binding.emptyStateContainer.visibility = View.GONE
        }
    }
}
