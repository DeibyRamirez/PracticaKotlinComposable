package com.cheiviz.practicacomposer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cheiviz.practicacomposer.screens.components.AddTaskDialog
import com.cheiviz.practicacomposer.screens.components.Task
import com.cheiviz.practicacomposer.screens.components.TaskDataStore
import com.cheiviz.practicacomposer.screens.components.TaskRow
import kotlinx.coroutines.launch

// Colores mejorados
val PrimaryColor = Color(0xFF2196F3)
val PrimaryLightColor = Color(0xFF6EC6FF)
val PrimaryDarkColor = Color(0xFF0069C0)
val SurfaceColor = Color(0xFFFFFFFF)
val BackgroundGradientStart = Color(0xFFE3F2FD)
val BackgroundGradientEnd = Color(0xFFBBDEFB)
val TextPrimary = Color(0xFF212121)
val TextSecondary = Color(0xFF757575)
val dataStoreName = "user_preferences"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(userName: String, onLogout: () -> Unit, taskDataStore: TaskDataStore) {

    val scope = rememberCoroutineScope()

    val tasks by taskDataStore.tasksFlow(userName).collectAsState(initial = emptyList())

    var showAddDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(120.dp),
                title = {
                    Text(
                        text = "Tareas",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,

                        color = Color.White

                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryColor,
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    // Tarjeta de usuario mejorada
                    Card(
                        modifier = Modifier
                            .padding(end = 20.dp).widthIn( 150.dp )
                            .shadow(4.dp, RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(
                            containerColor = SurfaceColor
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            // Avatar del usuario
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryLightColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = "Usuario",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Column {
                                Text(
                                    text = "Hola,",
                                    fontSize = 12.sp,
                                    color = TextSecondary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = userName,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextPrimary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.width(120.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(5.dp))

                            TextButton(
                                onClick = onLogout,
                                modifier = Modifier.height(36.dp)
                            ) {
                                Text(
                                    "Salir",
                                    color = PrimaryColor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = PrimaryColor,
                modifier = Modifier
                    .shadow(8.dp, CircleShape)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Agregar tarea",
                    tint = Color.White
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        // Fondo degradado mejorado

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(BackgroundGradientStart, BackgroundGradientEnd)
                    )
                )
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                // Header con estadísticas
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 16.dp)
                        .shadow(4.dp, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = SurfaceColor
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = tasks.size.toString(),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryColor
                            )
                            Text(
                                text = "Total",
                                fontSize = 14.sp,
                                color = TextSecondary
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = tasks.count { it.isDone }.toString(),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4CAF50)
                            )
                            Text(
                                text = "Completadas",
                                fontSize = 14.sp,
                                color = TextSecondary
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = tasks.count { !it.isDone }.toString(),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF9800)
                            )
                            Text(
                                text = "Pendientes",
                                fontSize = 14.sp,
                                color = TextSecondary
                            )
                        }
                    }
                }

                // Lista de tareas
                if (tasks.isEmpty()) {
                    // Estado vacío mejorado
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(40.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryLightColor.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = null,
                                    tint = PrimaryColor,
                                    modifier = Modifier.size(40.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = "No hay tareas",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Presiona el botón + para agregar tu primera tarea",
                                fontSize = 16.sp,
                                color = TextSecondary,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(tasks, key = { it.id }) { task ->
                            TaskRow(
                                task = task,
                                onToggle = {
                                    val updated = tasks.map {
                                        if (it.id == task.id) it.copy(isDone = !it.isDone) else it
                                    }
                                    scope.launch { taskDataStore.saveTasks( userName,updated) }
                                },
                                onDelete = {
                                    val update = tasks - task
                                    scope.launch {
                                        taskDataStore.saveTasks(userName,update)
                                        snackbarHostState.showSnackbar(
                                            "Tarea eliminada",
                                            withDismissAction = true
                                        )
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
                val nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
                val newTask = Task(nextId, title)
                scope.launch {
                    taskDataStore.saveTasks(userName,tasks + newTask)
                    snackbarHostState.showSnackbar(
                        "Tarea agregada",
                        withDismissAction = true
                    )
                }
                showAddDialog = false
            },
            onDismiss = { showAddDialog = false }
        )
    }
}

@Preview(showSystemUi = true, )
@Composable
fun PreviewHomeScreen() {
    HomeScreen(userName = "Juan Pérez", onLogout = {}, taskDataStore = TaskDataStore(LocalContext.current))
}

@Preview(showSystemUi = true, )
@Composable
fun PreviewHomeScreenWithTasks() {
    val sampleTasks = remember {
        mutableStateListOf(
            Task(1, "Comprar víveres", false),
            Task(2, "Reunión con el equipo", true),
            Task(3, "Estudiar Kotlin", false)
        )
    }

    // Simulamos una pantalla con tareas
    HomeScreen(userName = "María García", onLogout = {},taskDataStore = TaskDataStore(LocalContext.current))
}