package com.cheiviz.practicacomposer.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import kotlinx.serialization.Serializable


// Colores para las tareas
val TaskPrimary = Color(0xFF2196F3)
val TaskSuccess = Color(0xFF4CAF50)
val TaskError = Color(0xFFF44336)
val TaskSurface = Color(0xFFFFFFFF)
val TaskTextPrimary = Color(0xFF212121)
val TaskTextSecondary = Color(0xFF757575)
val TaskBackground = Color(0xFFF5F5F5)
val TaskCheckboxBorder = Color(0xFFE0E0E0)

// Modelo de Datos
@Serializable
data class Task(
    val id: Int,
    val title: String,
    val isDone: Boolean = false)

// Vista de las tareas creadas - Diseño mejorado
@Composable
fun TaskRow(task: Task, onToggle: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            ),
        colors = CardDefaults.cardColors(
            containerColor = TaskSurface
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox circular mejorado
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        if (task.isDone) {
                            Brush.verticalGradient(
                                colors = listOf(TaskSuccess, Color(0xFF388E3C))
                            )
                        } else {
                            Brush.verticalGradient(
                                colors = listOf(Color.White, TaskBackground)
                            )
                        }
                    )
                    .shadow(
                        elevation = if (task.isDone) 0.dp else 2.dp,
                        shape = CircleShape,
                        clip = false
                    ),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onToggle,
                    modifier = Modifier.size(44.dp)
                ) {
                    if (task.isDone) {
                        Icon(
                            Icons.Outlined.CheckCircle,
                            contentDescription = "Tarea completada",
                            tint = Color.Green,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Marcar como completada",
                            tint = TaskCheckboxBorder,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Contenido de la tarea
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = buildAnnotatedString {
                        if (task.isDone) {
                            withStyle(
                                style = SpanStyle(
                                    textDecoration = TextDecoration.LineThrough,
                                    color = TaskTextSecondary
                                )
                            ) {
                                append(task.title)
                            }
                        } else {
                            append(task.title)
                        }
                    },
                    fontSize = 16.sp,
                    fontWeight = if (task.isDone) FontWeight.Normal else FontWeight.Medium,
                    color = if (task.isDone) TaskTextSecondary else TaskTextPrimary,
                    maxLines = 2
                )

                if (task.isDone) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Completada",
                        fontSize = 12.sp,
                        color = TaskSuccess,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Botón de eliminar mejorado
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0x1AFF5252))
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Eliminar tarea",
                    tint = TaskError,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// Dialog de agregar tarea mejorado
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskDialog(onAdd: (String) -> Unit, onDismiss: () -> Unit) {
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = true),
        modifier = Modifier.shadow(24.dp, RoundedCornerShape(28.dp))
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            shape = RoundedCornerShape(28.dp),
            color = TaskSurface
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // Header del dialog
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(TaskPrimary, Color(0xFF1976D2))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Nueva tarea",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        "Nueva Tarea",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TaskTextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Campo de texto
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = {
                        Text(
                            "¿Qué necesitas hacer?",
                            color = TaskTextSecondary
                        )
                    },
                    placeholder = {
                        Text(
                            "Ej: Comprar víveres para la semana",
                            color = TaskTextSecondary.copy(alpha = 0.6f)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Botones de acción
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            "Cancelar",
                            color = TaskTextSecondary,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Button(
                        onClick = {
                            if (text.isNotBlank()) {
                                onAdd(text)
                                text = ""
                            }
                        },
                        enabled = text.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TaskPrimary,
                            disabledContainerColor = TaskTextSecondary.copy(alpha = 0.3f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "Agregar",
                            fontWeight = FontWeight.SemiBold,
                            color = if (text.isNotBlank()) Color.White else Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}

// Preview de tareas en diferentes estados
@Preview
@Composable
fun PreviewTaskRow() {
    Column(
        modifier = Modifier
            .background(TaskBackground)
            .padding(16.dp)
    ) {
        TaskRow(
            task = Task(1, "Comprar víveres para la cena familiar", false),
            onToggle = {},
            onDelete = {}
        )

        Spacer(modifier = Modifier.height(8.dp))

        TaskRow(
            task = Task(2, "Reunión con el equipo de desarrollo", true),
            onToggle = {},
            onDelete = {}
        )

        Spacer(modifier = Modifier.height(8.dp))

        TaskRow(
            task = Task(3, "Estudiar Kotlin y Jetpack Compose para el proyecto nuevo", false),
            onToggle = {},
            onDelete = {}
        )
    }
}

@Preview
@Composable
fun PreviewAddTaskDialog() {
    AddTaskDialog(
        onAdd = {},
        onDismiss = {}
    )
}