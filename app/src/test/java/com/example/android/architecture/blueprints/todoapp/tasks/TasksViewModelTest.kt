package com.example.android.architecture.blueprints.todoapp.tasks

import android.service.autofill.Validators.not
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {

    @Test
    fun addNewTask_setsNewTask() {

        /*
        * This rule runs all architecture components related background jobs in the same thread,
        * ensuring that the test results happen synchronously and in a repeatable order
        */
        @get:Rule
        var instantTaskExecutorRule = InstantTaskExecutorRule()

        // Given a fresh TasksViewModel
        // In this particular case we need an application (the ViewModel is an AndroidViewModel),
        // otherwise we wouldn't need is (if it were just a ViewModel)
        val tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())

        // When adding a new task
        tasksViewModel.addNewTask()

        // Then the new task event is triggered
        val value = tasksViewModel.newTaskEvent.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled(), not(nullValue()))
    }
}