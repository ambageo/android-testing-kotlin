package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual

class DefaultTasksRepositoryTest {
    private val task1 = Task("Title1", "Description1")
    private val task2 = Task("Title2", "Description2")
    private val task3 = Task("Title3", "Description3")
    private val remoteTasks = listOf(task1, task2).sortedBy { it.id }
    private val localTasks = listOf(task3).sortedBy { it.id }
    private val newTasks = listOf(task3).sortedBy { it.id }

    private lateinit var tasksRemoteDataSource: TasksDataSource
    private lateinit var tasksLocalDataSource: TasksDataSource

    //Class under test
    private lateinit var tasksRepository: DefaultTasksRepository

    @Before
    fun createRepository() {
        tasksRemoteDataSource = FakeDataSource(remoteTasks.toMutableList())
        tasksLocalDataSource = FakeDataSource(localTasks.toMutableList())
        // TODO Dispatchers.Unconfined should be replaced with Dispatchers.Main
        //  this requires understanding more about coroutines + testing
        //  so we will keep this as Unconfined for now.
        tasksRepository = DefaultTasksRepository(tasksRemoteDataSource, tasksLocalDataSource, Dispatchers.Unconfined)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getTasks_requestsAllTasksFromRemoteDataSource() = runBlockingTest{
        // When tasks are requested from the tasks repository
        val tasks = tasksRepository.getTasks(true) as Result.Success
        // Then tasks are loaded from the remote data source
        assertThat(tasks.data, IsEqual(remoteTasks))
    }
}