package ru.korobeynikov.p13roomtesting

import androidx.room.testing.MigrationTestHelper
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import ru.korobeynikov.p13roomtesting.database.AppDatabase

class MigrationTest {

    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java
    )

    @Test
    fun migrate1To2() {
        var db = helper.createDatabase(TEST_DB, 1)

        db.execSQL("INSERT INTO employee VALUES (1, 'John Smith', 10000)")
        db.execSQL("INSERT INTO employee VALUES (2, 'Ivan Ivanov', 20000)")
        db.execSQL("INSERT INTO employee VALUES (3, 'Petr Petrov', 30000)")
        db.close()

        db = helper.runMigrationsAndValidate(
            TEST_DB,
            2,
            true,
            AppDatabase.MIGRATION_1_2
        )

        val cursor = db.query("select * from employee")
        assertEquals(3, cursor.count)
        while (cursor.moveToNext()) {
            val salary = cursor.getInt(cursor.getColumnIndex("salary"))
            val taxclass = cursor.getInt(cursor.getColumnIndex("taxclass"))

            val expectedTaxClass =
                if (salary < 10000) 1
                else if (salary <= 20000) 2
                else 3
            assertEquals("Wrong taxclass for salary: $salary", expectedTaxClass, taxclass)
        }
    }

    companion object {
        private const val TEST_DB = "migrationTestDb"
    }
}