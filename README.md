# Task Management App

## Overview
This is a task management app that allows users to create, manage, and track tasks. The app includes a variety of features like creating tasks, viewing tasks with color-coded priorities, marking tasks as completed, notifications, and more. It is built using the MVVM architecture with Java and incorporates modern Android libraries and best practices.

## Features Implemented

### Core Features:
- **Create a New Task:**
  - Users can create tasks with a title, description, due date & time, and priority (Low, Medium, High).
  
- **View Tasks:**
  - Tasks are displayed in a list format with the following details:
    - Title
    - Due date and time
    - Priority (with color coding: green for Low, yellow for Medium, red for High)

- **Task Operations:**
  - **Update a Task**: Modify the task details.
  - **Delete a Task**: Remove a task from the list.
  - **Mark as Completed**: Tasks marked as completed will show a strike-through effect.

### Timer & Notifications:
- **Task Timer:** 
  - Each task has a timer to track its remaining time.

- **Task Due Notifications:**
  - Notifications appear 10 minutes before a task is due, showing the task title and due time.
  - Tapping the notification takes the user to the task details.


## Technical Details

- **Programming Language:** Java
- **Architecture:** MVVM (Model-View-ViewModel)
- **Persistence:** Room Database for task storage
- **UI:** Material Design Components
- **Task Scheduling:** AlarmManager for task notifications
- **Background Processing:** Executer for asynchronous background tasks
- **Notifications:** NotificationCompat for displaying task notifications

## Libraries & Tools Used
- **Room Database**: For storing task data locally.
- **AlarmManager**: For scheduling task notifications.
- **NotificationCompat**: For displaying notifications.
- **Material Design Components**: For a clean and modern UI.

## Getting Started

To set up the project locally, follow these steps:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/p4-programming/TaskManagementApp.git

2. **Screen Recording:**
   https://drive.google.com/file/d/1NCLhxNBcY_piOAjm-z2GvYFdCgBfa4h3/view?usp=sharing
