package ru.korobeynikov.data

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.korobeynikov.core.FileManager

@Module
class DataModule {
    @Provides
    fun provideDatabase(context: Context, fileManager: FileManager) = Database(context, fileManager)
}