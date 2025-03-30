package com.example.extendedtodo

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.extendedtodo.ui.theme.ToDoListAppTheme
import com.example.extendedtodo.components.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListAppTheme {
                TodoListScreen()
            }
        }
    }
}

@Composable
fun TodoListScreen() {
    val context = LocalContext.current

    // State: stores the added tasks
    val tasks = remember { mutableStateListOf<Task>() }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    TodoListContent(
        tasks = tasks,
        onAddTask = { title, imageUri ->
            tasks.add(Task(title, imageUri))
        },
        onEditTask = { task, title, imageUri ->
            tasks[tasks.indexOf(task)] = Task(title, imageUri)
        },
        onDeleteTask = { task ->
            tasks.remove(task)
        }
    )
}

@Composable
fun TodoListContent(
    tasks: List<Task>,
    onAddTask: (String, String?) -> Unit,
    onEditTask: (Task, String, String?) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    var newTaskTitle by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<String?>(null) }
    var editTaskTitle by remember { mutableStateOf("") }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Extended To Do List", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        AddTask(
            newTaskTitle = newTaskTitle,
            onTitleChange = { input -> newTaskTitle = input },
            selectedImageUri = selectedImageUri,
            onChooseImage = { image -> imagePickerLauncher.launch(image) },
            onAddTask = {
                if (newTaskTitle.isNotEmpty()) {
                    onAddTask(newTaskTitle, selectedImageUri)
                    newTaskTitle = ""
                    selectedImageUri = null
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TaskList(
            tasks = tasks,
            newTitle = editTaskTitle,
            onTitleChange = { input ->
                editTaskTitle = input
                            },
            onChangeImage = { image -> imagePickerLauncher.launch(image) },
            onEditTask = { task ->
                if (editTaskTitle.isNotEmpty()) {
                    onEditTask(task, editTaskTitle, selectedImageUri)
                    editTaskTitle = ""
                    selectedImageUri = null
                }
            },
            onDeleteTask = { task -> onDeleteTask(task) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToDoListAppTheme {
        TodoListScreen()
    }
}