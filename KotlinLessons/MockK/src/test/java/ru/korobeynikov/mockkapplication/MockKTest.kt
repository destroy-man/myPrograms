package ru.korobeynikov.mockkapplication

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.runs
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test

class MockKTest {

    @Test
    fun givenServiceMock_whenCallingMockedMethod_thenCorrectlyVerified() {
        val service = mockk<TestableService>()
        every {
            service.getDataFromDB("Expected Param")
        } returns "Expected Output"
        val result = service.getDataFromDB("Expected Param")
        verify {
            service.getDataFromDB("Expected Param")
        }
        assertEquals("Expected Output", result)
    }

    @Test
    fun givenServiceSpy_whenMockingOnlyOneMethod_thenOtherMethodsShouldBehaveAsOriginalObject() {
        val service = spyk<TestableService>()
        every {
            service.getDataFromDB(any())
        } returns "Mocked Output"
        val firstResult = service.getDataFromDB("Any Param")
        assertEquals("Mocked Output", firstResult)
        val secondResult = service.doSomethingElse("Any Param")
        assertEquals("I don't want to!", secondResult)
    }

    @Test
    fun givenRelaxedMock_whenCallingNotMockedMethod_thenReturnDefaultValue() {
        val service = mockk<TestableService>(relaxed = true)
        val result = service.getDataFromDB("Any Param")
        assertEquals("", result)
    }

    @Test
    fun givenObject_whenMockingIt_thenMockedMethodShouldReturnProperValue() {
        mockkObject(TestableServiceSingleton)
        val firstResult = TestableServiceSingleton.getDataFromDB("AnyParam")
        assertEquals("Expected Output", firstResult)
        every {
            TestableServiceSingleton.getDataFromDB(any())
        } returns "Mocked Output"
        val secondResult = TestableServiceSingleton.getDataFromDB("Any Param")
        assertEquals("Mocked Output", secondResult)
    }

    @Test
    fun givenHierarchicalClass_whenMockingIt_thenReturnProperValue() {
        val foo = mockk<Foo> {
            every { name } returns "Karol"
            every { bar } returns mockk {
                every { nickname } returns "Tomato"
            }
        }
        assertEquals("Karol", foo.name)
        assertEquals("Tomato", foo.bar.nickname)
    }

    @Test
    fun givenMock_whenCapturingParamValue_thenProperValueShouldBeCaptured() {
        val service = mockk<TestableService>()
        val slot = slot<String>()
        every {
            service.getDataFromDB(capture(slot))
        } returns "Expected Output"
        service.getDataFromDB("Expected Param")
        assertEquals("Expected Param", slot.captured)
    }

    @Test
    fun givenMock_whenCapturingParamsValues_thenProperValuesShouldBeCaptured() {
        val service = mockk<TestableService>()
        val list = mutableListOf<String>()
        every {
            service.getDataFromDB(capture(list))
        } returns "Expected Output"
        service.getDataFromDB("Expected Param 1")
        service.getDataFromDB("Expected Param 2")
        assertEquals(2, list.size)
        assertEquals("Expected Param 1", list[0])
        assertEquals("Expected Param 2", list[1])
    }

    @Test
    fun givenServiceMock_whenCallingMethodReturnsUnit_thenCorrectlyVerified() {
        val service = mockk<TestableService>()
        val myList = mutableListOf<String>()
        every {
            service.addHelloWorld(any())
        } just runs
        service.addHelloWorld(myList)
        assertTrue(myList.isEmpty())
    }

    @Test
    fun givenServiceMock_whenCallingOriginalMethod_thenCorrectlyVerified() {
        val service = mockk<TestableService>()
        val myList = mutableListOf<String>()
        every {
            service.addHelloWorld(any())
        } answers {
            callOriginal()
        }
        service.addHelloWorld(myList)
        assertEquals(1, myList.size)
        assertEquals("Hello World!", myList.first())
    }

    @Test
    fun givenServiceMock_whenStubbingTwoScenarios_thenCorrectlyVerified() {
        val service = mockk<TestableService>()
        val kaiList = mutableListOf("Kai")
        val emptyList = mutableListOf<String>()
        every {
            service.addHelloWorld(any())
        } just runs
        every {
            service.addHelloWorld(match { "Kai" in it })
        } answers {
            callOriginal()
        }
        service.addHelloWorld(kaiList)
        service.addHelloWorld(emptyList)
        assertEquals(listOf("Kai", "Hello World!"), kaiList)
        assertTrue(emptyList.isEmpty())
    }
}