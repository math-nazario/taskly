package com.example.taskly.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taskly.database.AppDatabase
import com.example.taskly.databinding.ActivityTaskListBinding
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
    }

    private fun goToTaskActivity() {
        val intent = Intent(this, TaskActivity::class.java)
        startActivity(intent)
    }
}
