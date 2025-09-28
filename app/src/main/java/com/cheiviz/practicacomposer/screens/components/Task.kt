package com.cheiviz.practicacomposer.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties

// Modelo de Datos
data class Task(val id: Int, val title: String, val isDone: Boolean = false)


// Vista de las tareas creadas
@Composable
fun TaskRow(task: Task, onToggle: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            IconButton(onClick = {
                onToggle()
            }) {
                if (task.isDone) Icon(Icons.Default.Check, contentDescription = "Hecho")
                else Icon(Icons.Default.Check, contentDescription = "Marcar")
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    task.title,
                    fontWeight = if (task.isDone) FontWeight.Light else FontWeight.Medium
                )
                if (task.isDone) Text("Tarea completada", fontSize = 12.sp, color = Color.Gray)
            }

            IconButton(onClick = {
                onDelete()
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }

        }

    }

}


@Composable
fun AddTaskDialog(onAdd: (String) -> Unit, onDismiss: () -> Unit) {
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nueva tarea") },

        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Titulo") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Add, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )
        },

        confirmButton = {
            TextButton(onClick = {
                if (text.isNotBlank()) {
                    onAdd(text)
                    text = ""
                }
            }) {
                Text("Añadir")

            }
        },

        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }

    )
}

@Preview
@Composable
fun PreviewTask(){
    TaskRow(task = Task(1, "Tarea de ejemplo"), onToggle = {}, onDelete = {})
}