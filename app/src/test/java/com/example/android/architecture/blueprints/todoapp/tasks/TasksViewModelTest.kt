package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {

    @Test
    fun addNewTask_setsNewTask() {
        // Given a fresh TasksViewModel
        // In this particular case we need an application (the ViewModel is an AndroidViewModel),
        // otherwise we wouldn't need is (if it were just a ViewModel)
        val tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())

        // When adding a new task
        tasksViewModel.addNewTask()


        // Then the new task event is triggered
    }
}