package com.example.taskmanagement.view.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagement.R;
import com.example.taskmanagement.model.TaskModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.graphics.Paint;
import android.widget.CheckBox;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    public List<TaskModel> taskList = new ArrayList<>();
    private final OnTaskActionListener listener;

    public TaskAdapter(OnTaskActionListener listener) {
        this.listener = listener;
    }

    public void setTasks(List<TaskModel> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    public void updateTasks(List<TaskModel> newTasks) {
        taskList.clear();
        taskList.addAll(newTasks);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskModel task = taskList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTaskTitle, tvTaskDueDate,vPriorityIndicator;
        private final CheckBox cbCompleted;
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvTaskDueDate = itemView.findViewById(R.id.tvTaskDueDate);
            cbCompleted = itemView.findViewById(R.id.cbCompleted);
            vPriorityIndicator = itemView.findViewById(R.id.vPriorityIndicator);
        }

        public void bind(TaskModel task) {
            tvTaskTitle.setText(task.getTitle());
            tvTaskDueDate.setText(dateFormat.format(task.getDueDate()));

            cbCompleted.setOnCheckedChangeListener(null);

            if (task.isCompleted()) {
                tvTaskTitle.setPaintFlags(tvTaskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                tvTaskTitle.setText(task.getTitle());
                cbCompleted.setChecked(true);
            } else {
                tvTaskTitle.setPaintFlags(tvTaskTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                cbCompleted.setChecked(false);
            }

            int color;
            String priority = "";
            switch (task.getPriority()) {
                case 1:
                    color = itemView.getContext().getColor(android.R.color.holo_green_light);
                    priority = "Low";
                    break;
                case 2:
                    color = itemView.getContext().getColor(android.R.color.holo_orange_light);
                    priority = "Medium";
                    break;
                case 3:
                    color = itemView.getContext().getColor(android.R.color.holo_red_light);
                    priority = "High";
                    break;
                default:
                    color = itemView.getContext().getColor(android.R.color.darker_gray);
                    priority = "Default";
            }
            vPriorityIndicator.setBackgroundColor(color);
            vPriorityIndicator.setText(priority);
            vPriorityIndicator.setTextColor(Color.WHITE);

            cbCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
                task.setCompleted(isChecked);
                listener.onTaskUpdated(task);
            });

            itemView.setOnClickListener(v -> listener.onTaskSelected(task));
        }
    }

    public interface OnTaskActionListener {
        void onTaskUpdated(TaskModel task);
        void onTaskSelected(TaskModel task);
    }
}
