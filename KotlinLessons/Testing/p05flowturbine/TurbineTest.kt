package ru.korobeynikov.p05flowturbine

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.milliseconds

class TurbineTest {

    @Test
    fun singleFlowExample() = runTest {
        flowOf("one").test {
            assertEquals("one", awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun multipleFlowsExample() = runTest {
        turbineScope {
            val turbine1 = flowOf(1).testIn(backgroundScope)
            val turbine2 = flowOf(2).testIn(backgroundScope)
            assertEquals(1, turbine1.awaitItem())
            assertEquals(2, turbine2.awaitItem())
            turbine1.awaitComplete()
            turbine2.awaitComplete()
        }
    }

    @Test
    fun receiveFirstElementExample() = runTest {
        flowOf("one", "two").test {
            assertEquals("one", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun receiveSecondElementExample() = runTest {
        flowOf("one", "two", "three").map {
            delay(100.milliseconds)
            it
        }.test {
            // 0 - 100ms -> элементов еще нет
            // 100ms - 200ms -> получен первый элемент
            // 200ms - 300ms -> получен второй элемент
            // 300ms - 400ms -> получен третий элемент
            delay(250.milliseconds)
            assertEquals("two", expectMostRecentItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun exceptionExample() = runTest {
        flow<Nothing> { throw RuntimeException("broken!") }.test {
            assertEquals("broken!", awaitError().message)
        }
    }

    @Test
    fun concurrencyExample() = runTest {
        channelFlow {
            withContext(Dispatchers.IO) {
                repeat(10) {
                    Thread.sleep(200)
                    send("item $it")
                }
            }
        }.test {
            Thread.sleep(700)
            cancel()
            assertEquals("item 0", awaitItem())
            assertEquals("item 1", awaitItem())
            assertEquals("item 2", awaitItem())
        }
    }

    @Test
    fun namedTurbinesExample() = runTest {
        turbineScope {
            val turbine1 = flowOf(1).testIn(backgroundScope, name = "turbine 1")
            val turbine2 = flowOf(2).testIn(backgroundScope, name = "turbine 2")
            //assertEquals(1,turbine1.awaitItem())
            //assertEquals(2,turbine2.awaitItem())
            turbine1.awaitComplete()
            turbine2.awaitComplete()
        }
    }

    @Test
    fun orderExecutionSharedFlowExample() = runTest {
        val mutableSharedFlow = MutableSharedFlow<Int>(0)
        mutableSharedFlow.test {
            mutableSharedFlow.emit(1)
            assertEquals(awaitItem(), 1)
        }
    }

    @Test
    fun stateFlowChangeBeforeTestExample() = runTest {
        val mutableStateFlow = MutableStateFlow(0)
        mutableStateFlow.value = 1
        mutableStateFlow.test {
            assertEquals(awaitItem(), 1)
        }
    }

    @Test
    fun stateFlowChangeAfterTestExample() = runTest {
        val mutableStateFlow = MutableStateFlow(0)
        mutableStateFlow.test {
            assertEquals(awaitItem(), 0)
            mutableStateFlow.value = 1
            assertEquals(awaitItem(), 1)
        }
    }

    @Test
    fun timeoutTurbineExample() = runTest {
        flowOf("one", "two").test(timeout = 10.milliseconds) {
            assertEquals("one", awaitItem())
            assertEquals("two", awaitItem())
            awaitComplete()
        }
    }
}