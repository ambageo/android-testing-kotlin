package com.example.android.architecture.blueprints.todoapp.statistics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StatisticsViewModelTest{
    private lateinit var statisticsViewModel: StatisticsViewModel
    // Use a fake repository to be injected into the viewModel
    private lateinit var tasksRepository: FakeTestRepository

    /*
   * This rule runs all architecture components related background jobs in the same thread,
   * ensuring that the test results happen synchronously and in a repeatable order
   */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUpViewModel(){
        tasksRepository = FakeTestRepository()

        statisticsViewModel = StatisticsViewModel((tasksRepository))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun loadTasks_loading() {

        // Pause the dispatcher so that you can verify initial values. This way, inside the refresh()
        // only the  _dataLoading.value = true
        mainCoroutineRule.pauseDispatcher()

        // Load the task in the view model.
        statisticsViewModel.refresh()

        // Then progress indicator is shown.
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), `is`(true))

        // Execute pending coroutines actions, _dataLoading.value turns false
        mainCoroutineRule.resumeDispatcher()

        // Then progress indicator is hidden.
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), `is`(false))
    }

}