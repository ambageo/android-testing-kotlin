package com.example.android.architecture.blueprints.todoapp.statistics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

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

}