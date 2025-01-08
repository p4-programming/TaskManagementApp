package com.example.taskmanagement.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.taskmanagement.model.TaskModel;
import com.example.taskmanagement.repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<TaskModel>> allTasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = repository.getAllTasks();
    }

    public long insert(TaskModel task) {
        return repository.insert(task);
    }

    public void update(TaskModel task) {
        repository.update(task);
    }

    public void delete(TaskModel task) {
        repository.delete(task);
    }

    public LiveData<TaskModel> getTaskById(int id) {
        return repository.getTaskById(id);
    }

    public LiveData<List<TaskModel>> getAllTasks() {
        return allTasks;
    }

    public LiveData<List<TaskModel>> searchTasks(String query) {
        return repository.searchTasks(query);
    }

    public LiveData<List<TaskModel>> filterTasksByPriority(int priority) {
        return repository.filterTasksByPriority(priority);
    }

    public LiveData<List<TaskModel>> filterTasksByStatus(boolean status) {
        return repository.filterTasksByStatus(status);
    }
}
