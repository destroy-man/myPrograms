package ru.korobeynikov.p20roomautomigrationspec.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteTable
import androidx.room.RenameTable
import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [Employee::class],
    version = 5,
    autoMigrations = [
        AutoMigration(1, 2, spec = AppDatabase.DeleteTableAutoMigration::class),
        AutoMigration(2, 3, spec = AppDatabase.RenameTableAutoMigration::class),
        AutoMigration(3, 4, spec = AppDatabase.DeleteColumnAutoMigration::class),
        AutoMigration(4, 5, spec = AppDatabase.RenameColumnAutoMigration::class)
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    //v1
    //abstract fun carDao(): CarDao

    //v2
    @DeleteTable(tableName = "Car")
    class DeleteTableAutoMigration : AutoMigrationSpec

    //v3
    @RenameTable(fromTableName = "Employee", toTableName = "Staff")
    class RenameTableAutoMigration : AutoMigrationSpec

    //v4
    @DeleteColumn(tableName = "Staff", columnName = "salary")
    class DeleteColumnAutoMigration : AutoMigrationSpec

    //v5
    @RenameColumn(tableName = "Staff", fromColumnName = "name", toColumnName = "full_name")
    class RenameColumnAutoMigration : AutoMigrationSpec
}