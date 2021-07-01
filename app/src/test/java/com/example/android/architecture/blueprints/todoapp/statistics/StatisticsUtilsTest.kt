package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test

class StatisticsUtilsTest {

    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsHundredZero(){
        val tasks = listOf<Task>(
            Task("title", "description", false)
        )
        val result = getActiveAndCompletedStats(tasks)
        assertEquals(result.activeTasksPercent, 100f)
        // Same but with hamcrest - assertThat deprecated
        assertThat(result.activeTasksPercent , `is` (100f))
        assertEquals(result.completedTasksPercent, 0f)
        // Same but with hamcrest
        assertThat(result.completedTasksPercent, `is` (0f))
    }

    @Test
    fun getActiveAndCompletedStats_empty_returnsZero(){
        val tasks = emptyList<Task>()

        val result = getActiveAndCompletedStats(tasks)
        assertEquals(result.activeTasksPercent, 0f)
        assertEquals(result.completedTasksPercent, 0f)
    }

    @Test
    fun getActiveAndCompletedStats_null_returnsZero(){
        val tasks = null
        val result = getActiveAndCompletedStats(tasks)
        assertEquals(result.activeTasksPercent, 0f)
        assertEquals(result.completedTasksPercent, 0f)
    }

}