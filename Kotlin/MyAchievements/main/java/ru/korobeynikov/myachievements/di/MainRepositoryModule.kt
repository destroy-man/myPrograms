package ru.korobeynikov.myachievements.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.korobeynikov.myachievements.achievement.MainRepository
import ru.korobeynikov.myachievements.database.AchievementDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainRepositoryModule {

    @Provides
    fun provideMainRepository(db: AchievementDatabase, retrofit: Retrofit): MainRepository {
        return MainRepository(db, retrofit)
    }

    @Singleton
    @Provides
    fun provideAchievementDatabase(@ApplicationContext context: Context): AchievementDatabase {
        return Room.databaseBuilder(context, AchievementDatabase::class.java, "database").build()
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://api.steampowered.com/ISteamUserStats/GetGlobalAchievementPercentagesForApp/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
}