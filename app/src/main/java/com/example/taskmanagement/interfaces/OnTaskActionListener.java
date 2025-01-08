package com.example.taskmanagement.interfaces;

import com.example.taskmanagement.model.TaskModel;

public interface OnTaskActionListener {
    void onTaskUpdated(TaskModel task);
    void onTaskSelected(TaskModel task);
}