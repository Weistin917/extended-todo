package com.example.extendedtodo.components

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddTask() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = { Text("Task Name") },
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = {}) {
            Text("Choose an image")
        }
        Button(onClick = {}) {
            Text("Add Task")
        }
    }
}