package com.example.taskmanagement.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmanagement.model.TaskModel;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    long insert(TaskModel task);

    @Update
    void update(TaskModel task);

    @Delete
    void delete(TaskModel task);

    @Query("SELECT * FROM task_table ORDER BY dueDate ASC")
    LiveData<List<TaskModel>> getAllTasks();

    @Query("SELECT * FROM task_table WHERE id = :id")
    LiveData<TaskModel> getTaskById(int id);

    @Query("SELECT * FROM task_table WHERE title LIKE :searchQuery ORDER BY dueDate ASC")
    LiveData<List<TaskModel>> searchTasks(String searchQuery);

    @Query("SELECT * FROM task_table WHERE priority = :priority ORDER BY dueDate ASC")
    LiveData<List<TaskModel>> filterTasksByPriority(int priority);

    @Query("SELECT * FROM task_table WHERE isCompleted = :status ORDER BY dueDate ASC")
    LiveData<List<TaskModel>> filterTasksByStatus(boolean status);
}
