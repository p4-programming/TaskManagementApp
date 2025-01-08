package com.example.taskmanagement.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.taskmanagement.database.TaskDao;
import com.example.taskmanagement.database.TaskDatabase;
import com.example.taskmanagement.model.TaskModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<TaskModel>> allTasks;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public TaskRepository(Application application) {
        TaskDatabase database = TaskDatabase.getInstance(application);
        taskDao = database.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public long insert(TaskModel task) {
        return taskDao.insert(task);
    }

    public void update(TaskModel task) {
        executorService.execute(() -> taskDao.update(task));
    }

    public void delete(TaskModel task) {
        executorService.execute(() -> taskDao.delete(task));
    }

    public LiveData<List<TaskModel>> getAllTasks() {
        return allTasks;
    }

    public LiveData<TaskModel> getTaskById(int id) {
        return taskDao.getTaskById(id);
    }

    public LiveData<List<TaskModel>> searchTasks(String query) {
        return taskDao.searchTasks(query);
    }

    public LiveData<List<TaskModel>> filterTasksByPriority(int priority) {
        return taskDao.filterTasksByPriority(priority);
    }

    public LiveData<List<TaskModel>> filterTasksByStatus(boolean status) {
        return taskDao.filterTasksByStatus(status);
    }
}
