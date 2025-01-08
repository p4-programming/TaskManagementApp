package com.example.taskmanagement.view.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmanagement.R;

import androidx.appcompat.widget.SearchView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.model.TaskModel;
import com.example.taskmanagement.view.adapters.TaskAdapter;
import com.example.taskmanagement.viewmodel.TaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private TaskViewModel taskViewModel;
    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recyclerView);

        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);

        searchView.setVisibility(View.VISIBLE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        taskAdapter = new TaskAdapter(new TaskAdapter.OnTaskActionListener() {
            @Override
            public void onTaskUpdated(TaskModel task) {
                taskViewModel.update(task);
            }

            @Override
            public void onTaskSelected(TaskModel task) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                intent.putExtra("taskId", task.getId());
                startActivity(intent);
            }
        });


        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, tasks -> taskAdapter.setTasks(tasks));

        recyclerView.setAdapter(taskAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });
        setupRecyclerView();
        setupSearch();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAbsoluteAdapterPosition();
                TaskModel taskToDelete = taskAdapter.taskList.get(position);
                taskViewModel.delete(taskToDelete);

                Toast.makeText(MainActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }


    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchTasks(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchTasks(newText);
                return true;
            }

            private void searchTasks(String query) {
                taskViewModel.searchTasks("%" + query + "%").observe(MainActivity.this, tasks -> {
                    taskAdapter.updateTasks(tasks);
                });
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.completion_Status) {
            Toast.makeText(this, "status", Toast.LENGTH_SHORT).show();
            return true;
        }else if (id == R.id.short_Priority) {
            Toast.makeText(this, "shortPriority", Toast.LENGTH_SHORT).show();
            return true;
        }else if (id == R.id.show_all_task) {
            taskViewModel.getAllTasks().observe(this, tasks -> taskAdapter.setTasks(tasks));
            Toast.makeText(this, "Show All Task", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
