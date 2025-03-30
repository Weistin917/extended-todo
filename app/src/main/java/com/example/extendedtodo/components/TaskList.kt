package com.example.extendedtodo.components

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TaskList(tasks: List<Task>) {
    Column {
        tasks.forEach { task ->
            TaskItem(task)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun TaskItem(task: Task) {

}

data class Task(val title: String, val imageUri: String?)