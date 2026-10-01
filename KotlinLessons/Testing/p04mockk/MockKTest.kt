package ru.korobeynikov.p04mockk

import android.util.Log
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.slot
import io.mockk.spyk
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import ru.korobeynikov.p04mockk.testableservice.InjectTestService
import ru.korobeynikov.p04mockk.testableservice.TestableService
import ru.korobeynikov.p04mockk.testableservice.TestableServiceSingleton

class MockKTest {

    @Test
    fun basicExample() {
        //given
        val service = mockk<TestableService>()
        every { service.getDataFromDb("Expected Param") } returns "Expected Output"

        //when
        val result = service.getDataFromDb("Expected Param")

        //then
        verify { service.getDataFromDb("Expected Param") }
        assertEquals("Expected Output", result)
    }

    @MockK
    lateinit var service1: TestableService

    @MockK
    lateinit var service2: TestableService

    @InjectMockKs
    var objectUnderTest = InjectTestService()

    @Test
    fun annotationExample() {
        MockKAnnotations.init(this)
        println("service 1 = $service1, service 2 = $service2, object under test = $objectUnderTest")
    }

    //Создание spy объекта через аннотацию
    //@SpyK
    //var service = TestableService()

    @Test
    fun spyExample() {
        //Создание spy объекта через аннотацию
        //MockKAnnotations.init(this)
        val service = spyk<TestableService>()
        every { service.getDataFromDb(any()) } returns "Mocked Output"

        //checking mocked method
        val firstResult = service.getDataFromDb("Any Param")

        assertEquals("Mocked Output", firstResult)

        //checking not mocked method
        val secondResult = service.doSomethingElse("Any Param")

        assertEquals("I don't want to!", secondResult)
    }

    //Создание relaxed mock объекта через аннотацию
    //@RelaxedMockK
    //lateinit var service: TestableService

    @Test
    fun relaxedExample() {
        //Создание relaxed mock объекта через аннотацию
        //MockKAnnotations.init(this)
        val service = mockk<TestableService>(relaxed = true)

        val result = service.getDataFromDb("Any Param")

        assertEquals("", result)
    }

    @Test
    fun mockObjectExample() {
        mockkObject(TestableServiceSingleton)

        val firstResult = TestableServiceSingleton.getDataFromDb("Any Param")

        assertEquals("data", firstResult)

        every { TestableServiceSingleton.getDataFromDb(any()) } returns "Mocked Output"
        val secondResult = TestableServiceSingleton.getDataFromDb("Any Param")

        assertEquals("Mocked Output", secondResult)
    }

    @Test
    fun hierarchyStructureExample() {
        val foo = mockk<Foo> {
            every { name } returns "Karol"
            every { bar } returns mockk {
                every { nickname } returns "Tomato"
            }
        }

        val name = foo.name
        val nickname = foo.bar.nickname

        assertEquals("Karol", name)
        assertEquals("Tomato", nickname)
    }

    @Test
    fun capturingParameterExample() {
        val service = mockk<TestableService>()
        val slot = slot<String>()
        every { service.getDataFromDb(capture(slot)) } returns "Expected Output"

        service.getDataFromDb("Expected Param")

        assertEquals("Expected Param", slot.captured)
    }

    @Test
    fun capturingParametersExample() {
        val service = mockk<TestableService>()
        val list = mutableListOf<String>()
        every { service.getDataFromDb(capture(list)) } returns "Expected Output"

        service.getDataFromDb("Expected Param 1")
        service.getDataFromDb("Expected Param 2")

        assertEquals(2, list.size)
        assertEquals("Expected Param 1", list[0])
        assertEquals("Expected Param 2", list[1])
    }

    @Test
    fun unitMethodJustRunsExample() {
        val service = mockk<TestableService>()
        val myList = mutableListOf<String>()

        //just runs пропускает вызов Unit метода
        every { service.addHelloWorld(any()) } just runs
        service.addHelloWorld(myList)

        assertTrue(myList.isEmpty())
    }

    @Test
    fun unitMethodCallOriginalExample() {
        val service = mockk<TestableService>()
        val myList = mutableListOf<String>()

        //Мокирование Log класса
        mockkStatic(Log::class)
        every { Log.d("myLogs", any()) } returns 0
        //callOriginal выполняет вызов Unit метода
        every { service.addHelloWorld(any()) } answers { callOriginal() }
        service.addHelloWorld(myList)

        assertEquals(1, myList.size)
        assertEquals("Hello World!", myList.first())

        unmockkStatic(Log::class)
    }

    @Test
    fun unitMethodCombinedExample() {
        val service = mockk<TestableService>()
        val kaiList = mutableListOf("Kai")
        val emptyList = mutableListOf<String>()

        every { service.addHelloWorld(any()) } just runs
        mockkStatic(Log::class)
        every { Log.d("myLogs", any()) } returns 0
        every { service.addHelloWorld(match { "Kai" in it }) } answers { callOriginal() }

        service.addHelloWorld(kaiList)
        service.addHelloWorld(emptyList)

        assertEquals(listOf("Kai", "Hello World!"), kaiList)
        assertTrue(emptyList.isEmpty())
    }
}