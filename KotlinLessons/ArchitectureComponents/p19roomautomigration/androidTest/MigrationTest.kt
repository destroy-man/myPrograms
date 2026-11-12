package ru.korobeynikov.p19roomautomigration

import androidx.room.testing.MigrationTestHelper
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import ru.korobeynikov.p19roomautomigration.database.AppDatabase

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
            true
        )
        db.execSQL("UPDATE employee SET birthday = 4 WHERE id = 1")
        db.execSQL("UPDATE employee SET birthday = 5 WHERE id = 2")
        db.execSQL("UPDATE employee SET birthday = 6 WHERE id = 3")

        val cursor = db.query("select * from employee")
        assertEquals(3, cursor.count)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val birthday = cursor.getInt(cursor.getColumnIndex("birthday"))

            val expectedBirthday = id + 3
            assertEquals(expectedBirthday, birthday)
        }
    }

    companion object {
        private const val TEST_DB = "migrationTestDb"
    }
}