package com.example.taskly.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.taskly.model.Task

@Dao
interface TaskDAO {
    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(product: Task)

    @Delete
    fun delete(product: Task)

    @Update
    fun update(task: Task)

    @Query("SELECT * FROM task WHERE id = :id")
    fun getById(id: Long): Task?
}
