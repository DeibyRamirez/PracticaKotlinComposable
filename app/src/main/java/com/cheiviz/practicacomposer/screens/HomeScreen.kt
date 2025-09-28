package com.cheiviz.practicacomposer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cheiviz.practicacomposer.screens.components.AddTaskDialog
import com.cheiviz.practicacomposer.screens.components.Task
import com.cheiviz.practicacomposer.screens.components.TaskRow
import kotlinx.coroutines.launch

// Pagina Principal
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(userName: String, onLogout: () -> Unit) {
    val tasks = remember { mutableStateListOf<Task>() }
    var nexId by remember { mutableStateOf(1) }

    var showAddDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("To-Do List") },
                actions = {
                    TextButton(onClick = { onLogout() }) {
                        Row(
                            modifier = Modifier.padding(end = 20.dp),
                        ) {
                            Icon(Icons.Default.Person, contentDescription = "Persona")
                            Text(
                                text = "$userName",
                                fontSize = 20.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->

        // 🔹 Aquí aplicamos el fondo con gradiente
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))
                    )
                )
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                if (tasks.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Text(
                                "No hay tareas...",
                                color = Color.Gray, fontSize = 20.sp
                            )

                            Text(
                                "Presiona + Para agregar una nueva",
                                color = Color.Gray, fontSize = 20.sp
                            )

                        }

                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(tasks, key = { it.id }) { task ->
                            TaskRow(
                                task = task,
                                onToggle = {
                                    val idx = tasks.indexOfFirst { it.id == task.id }
                                    if (idx >= 0) {
                                        tasks[idx] = tasks[idx].copy(isDone = !task.isDone)
                                    }
                                },
                                onDelete = {
                                    tasks.remove(task)
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Tarea eliminada")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddTaskDialog(
            onAdd = { title ->
                tasks.add(Task(id = nexId, title = title))
                nexId++
                showAddDialog = false
            },
            onDismiss = { showAddDialog = false }
        )
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen (userName = "", onLogout = {} )
}