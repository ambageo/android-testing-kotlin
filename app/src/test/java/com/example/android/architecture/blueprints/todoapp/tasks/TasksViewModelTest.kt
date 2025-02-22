package com.example.android.architecture.blueprints.todoapp.tasks


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.Event
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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

    // All this code is replaced by the MainCoroutineRule

    /*@ExperimentalCoroutinesApi
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    // Here we swap dispatchers because Dispatcher.Main cannot be used for tests
    @ExperimentalCoroutinesApi
    @Before
    fun setUpDispatcher(){
        Dispatchers.setMain(testDispatcher)
    }

    // Clean up after running the tests
    @ExperimentalCoroutinesApi
    @After
    fun tearDownDispatcher(){
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }*/

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

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

    @Test
    fun completeTask_dataAndSnackBarUpdated(){
        // Create and add task
        val task = Task("title", "Description")
        tasksRepository.addTasks(task)


        // WHEN - mark the task as complete
        tasksViewModel.completeTask(task, true)

        // THEN
        // Verify that the task is completed
        assertThat(tasksRepository.tasksServiceData[task.id]?.isCompleted, `is`(true))
        // Assert that the snackBar has been updated with the correct text
        val snackBarText: Event<Int> = tasksViewModel.snackbarText.getOrAwaitValue()
        assertThat(snackBarText.getContentIfNotHandled(), `is`(R.string.task_marked_complete))
    }
}