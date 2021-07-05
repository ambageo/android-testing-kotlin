package com.example.android.architecture.blueprints.todoapp.tasks

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {

    private lateinit var tasksViewModel: TasksViewModel

    // Use a fake repository to be injected into the viewModel
    private lateinit var tasksRepository: FakeTestRepository

    /*
    * This rule runs all architecture components related background jobs in the same thread,
    * ensuring that the test results happen synchronously and in a repeatable order
    */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Since we need a TaskViewModel shared between all tests, we can use @Before to set it
    @Before
    fun setUpViewModel() {
        // We initialise the tasks to 3, with one active and two completed
        tasksRepository = FakeTestRepository()
        val task1 = Task("Title1", "Description1")
        val task2 = Task("Title2", "Description2", true)
        val task3 = Task("Title3", "Description3", true)

        tasksRepository.addTasks(task1, task2, task3)

        tasksViewModel = TasksViewModel(tasksRepository)
        // No need to use AndroidX Test, so we can remove the @RunWith(AndroidJUnit4::class)
        //tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())
    }
    @Test
    fun addNewTask_setsNewTask() {

        // When adding a new task
        tasksViewModel.addNewTask()

        // Then the new task event is triggered
        val value = tasksViewModel.newTaskEvent.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled(), not(nullValue()))
        //The same
        //assertNotNull(value.getContentIfNotHandled())
    }

    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {

        // When the filter type is ALL_TASKS
        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)
        // Then the "Add task" action is visible
        assertThat(tasksViewModel.tasksAddViewVisible.getOrAwaitValue(), `is` (true))
    }
}