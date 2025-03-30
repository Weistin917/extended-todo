package com.example.extendedtodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.extendedtodo.ui.theme.ToDoListAppTheme
import com.example.extendedtodo.components.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListAppTheme {

            }
        }
    }
}

@Composable
fun TodoListScreen() {
    val tasks = listOf(Task("Examplo", "#"))

    TodoListContent(
        tasks = tasks,
        onAddTask = { title, imageUri ->
            // something
        }
    )
}

@Composable
fun TodoListContent(
    tasks: List<Task>,
    onAddTask: (String, String?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Extended To Do List", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        AddTask()

        Spacer(modifier = Modifier.height(16.dp))

        TaskList(tasks)
    }
}