package com.example.extendedtodo.components

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun AddTask(
    newTaskTitle: String,
    onTitleChange: (String) -> Unit,
    selectedImageUri: String?,
    onChooseImage: (String) -> Unit,
    onAddTask: () -> Unit
) {
    OutlinedTextField(
        value = newTaskTitle,
        onValueChange = { onTitleChange(it) },
        label = { Text("Task Name") },
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = { onChooseImage("image/*") }) {
            Text("Choose an image")
        }
        selectedImageUri?.let {
            uri -> AsyncImage(
                model = uri,
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
        }
        Button(onClick = {
            onAddTask()
        }) {
            Text("Add Task")
        }
    }
}