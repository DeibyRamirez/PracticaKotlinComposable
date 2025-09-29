package com.cheiviz.practicacomposer.screens.components

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import com.cheiviz.practicacomposer.screens.components.Task // 👈 importa tu Task

val Context.datastore by preferencesDataStore(name = "tasks_prefs")

class TaskDataStore(private val context: Context) {

    private val TASK_KEY = stringPreferencesKey("tasks")

    private val json = Json { ignoreUnknownKeys = true }

    // Obtiene las tareas de un usuario específico
    fun tasksFlow(user: String): Flow<List<Task>> {
        val userKey = stringPreferencesKey("tasks_$user")
        return context.datastore.data.map { preferences ->
            val stored = preferences[userKey] ?: "[]"
            json.decodeFromString(stored)
        }

    }

    // Guarda las tareas de un usuario específico

    suspend fun saveTasks(user: String,tasks: List<Task>) {
        val userKey = stringPreferencesKey("tasks_$user")
        context.datastore.edit { preferences ->
            preferences[userKey] = json.encodeToString(tasks)
        }
    }
}
