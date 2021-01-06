package com.debcomp.aql.sofietesteandroiddeveloper.feature.task.taskHome.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.entity.Task
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.repository.TaskRepository
import java.lang.IllegalArgumentException


/*
 * Davi Áquila
 * aquiladvx
 *
 * 05/01/2021
 *
 */

class TaskViewModel(val app: Application) : AndroidViewModel(app) {

    private val repository = TaskRepository(app)
    val allTasks = repository.allTasks
    val removeTask = repository.removeTask

    suspend fun getAllTasks(email: String) = repository.getAllTasks(email)

    suspend fun removeTask(task: Task, position: Int) = repository.removeTask(task.id, position)

    //Factory
    class TaskViewModelFactory constructor(private val application: Application) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
                TaskViewModel(this.application) as T
            } else {
                throw IllegalArgumentException("ViewModel not found")
            }
        }
    }
}