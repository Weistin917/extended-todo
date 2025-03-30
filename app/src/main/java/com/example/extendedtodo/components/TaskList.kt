package com.example.extendedtodo.components

import android.widget.Button
import android.widget.Space
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun TaskList(
    tasks: List<Task>,
    newTitle: String,
    onTitleChange: (String) -> Unit,
    onChangeImage: (String) -> Unit,
    onEditTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    var editTask by remember { mutableStateOf<Task?>(null) }

    Column {
        tasks.forEach { task ->
            if (editTask === task) {
                EditTaskItem(
                    editTask = task,
                    newTitle = newTitle,
                    onTitleChange = onTitleChange,
                    onChangeImage = onChangeImage,
                    onEditTask = onEditTask,
                    onCancelEdit = { editTask = null }
                )
            } else {
                TaskItem(
                    task = task,
                    setEditTask = { selectedTask ->
                        editTask = selectedTask
                        onTitleChange(selectedTask.title)
                                  },
                    onDeleteTask = onDeleteTask
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    setEditTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit
) {

    Card (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(text = task.title, style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(8.dp))

                task.imageUri?.let { uri ->
                    AsyncImage(
                        model = uri,
                        contentDescription = null,
                        modifier = Modifier.size(81.dp)
                    )
                }
            }
            Column() {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { setEditTask(task) }
                    ) {
                        Text(text = "Edit")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { onDeleteTask(task) }
                    ) {
                        Text(text = "Delete")
                    }
                }
            }

        }
    }
}

@Composable
fun EditTaskItem(
    editTask: Task,
    newTitle: String,
    onTitleChange: (String) -> Unit,
    onChangeImage: (String) -> Unit,
    onEditTask: (Task) -> Unit,
    onCancelEdit: () -> Unit
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                OutlinedTextField(
                    value = newTitle,
                    onValueChange = { onTitleChange(it) },
                    label = { Text(text = "Task Name") },
                    modifier = Modifier.width(100.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { onChangeImage("image/*") }) {
                    Text(text = "Change image")
                }
            }
            Column() {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { onCancelEdit() }
                    ) {
                        Text(text = "Cancel")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            onEditTask(editTask)
                            onCancelEdit()
                        }
                    ) {
                        Text(text = "Save")
                    }
                }
            }

        }
    }
}

data class Task(val title: String, val imageUri: String?)