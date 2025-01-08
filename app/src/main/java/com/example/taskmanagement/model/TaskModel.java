package com.example.taskmanagement.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class TaskModel {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private long dueDate;
    private int priority;
    private boolean isCompleted;

    public TaskModel(String title, String description, long dueDate, int priority, boolean isCompleted) {
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
        this.priority = priority;
        this.title = title;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public long getDueDate() {
        return dueDate;
    }
    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
