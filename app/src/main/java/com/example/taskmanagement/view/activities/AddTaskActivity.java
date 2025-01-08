package com.example.taskmanagement.view.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanagement.R;
import com.example.taskmanagement.model.TaskModel;
import com.example.taskmanagement.viewmodel.TaskViewModel;
import com.example.taskmanagement.worker.TaskScheduler;

import android.app.DatePickerDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.app.TimePickerDialog;

public class AddTaskActivity extends AppCompatActivity {
    private EditText etTitle, etDescription;
    private TextView tvDueDate;
    private RadioGroup rgPriority;
    private Button btnSave,btnDueDate;
    private TaskViewModel taskViewModel;
    private TaskModel currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        tvDueDate = findViewById(R.id.tvDueDate);
        rgPriority = findViewById(R.id.rgPriority);
        btnSave = findViewById(R.id.btnSaveTask);
        btnDueDate = findViewById(R.id.btnDueDate);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        if (getIntent().hasExtra("taskId")) {
            int taskId = getIntent().getIntExtra("taskId", -1);
            taskViewModel.getTaskById(taskId).observe(this, task -> {
                if (task != null) {
                    currentTask = task;
                    populateFields(task);
                }
            });
        }

        Intent intent = getIntent();
        long taskId = intent.getLongExtra("taskId_Again", -1);
        int id = (int) taskId;
        if (id >= 0){
            taskViewModel.getTaskById(id).observe(this, task -> {
                if (task != null) {
                    currentTask = task;
                    populateFields(task);
                }
            });
        }

        btnDueDate.setOnClickListener(v -> showDateTimePicker());
        btnSave.setOnClickListener(v -> saveTask());
    }

    private void populateFields(TaskModel task) {
        etTitle.setText(task.getTitle());
        etDescription.setText(task.getDescription());
        tvDueDate.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(task.getDueDate()));
        switch (task.getPriority()) {
            case 1: rgPriority.check(R.id.rbLow); break;
            case 2: rgPriority.check(R.id.rbMedium); break;
            case 3: rgPriority.check(R.id.rbHigh); break;
        }
    }

    private void saveTask() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String dueDateString = tvDueDate.getText().toString().trim();

        if (title.isEmpty() || dueDateString.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        int priority = 1;
        if (rgPriority.getCheckedRadioButtonId() == R.id.rbMedium) {
            priority = 2;
        } else if (rgPriority.getCheckedRadioButtonId() == R.id.rbHigh) {
            priority = 3;
        }

        try {
            Date dueDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).parse(dueDateString);
            long dueDateInMillis = dueDate.getTime();

            if (currentTask == null) {

                final int pri = priority;

                new Thread(() -> {
                    TaskModel newTask = new TaskModel(title, description, dueDateInMillis, pri, false);
                    long taskId = taskViewModel.insert(newTask);
                    if (taskId > 0) {
                        Log.i("TaskLog", "Task inserted successfully with ID: "+ taskId);
                    } else {
                        Log.i("TaskLog", "Failed to insert task.");
                    }
                    if (taskId > 0) {
                        TaskScheduler scheduler = new TaskScheduler();
                        scheduler.scheduleNotification(getApplicationContext(), title, dueDateInMillis, taskId);
                    }
                }).start();
            } else {
                currentTask.setTitle(title);
                currentTask.setDescription(description);
                currentTask.setDueDate(dueDateInMillis);
                currentTask.setPriority(priority);
                taskViewModel.update(currentTask);

                TaskScheduler scheduler = new TaskScheduler();
                scheduler.scheduleNotification(getApplicationContext(), title, dueDateInMillis, currentTask.getId());
            }

            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Invalid due date format.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        final Calendar selectedDate = Calendar.getInstance();

        new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            selectedDate.set(Calendar.YEAR, year);
            selectedDate.set(Calendar.MONTH, monthOfYear);
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            new TimePickerDialog(this, (timeView, hourOfDay, minute) -> {
                selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                selectedDate.set(Calendar.MINUTE, minute);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                tvDueDate.setText(sdf.format(selectedDate.getTime()));
            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH)).show();
    }
}
