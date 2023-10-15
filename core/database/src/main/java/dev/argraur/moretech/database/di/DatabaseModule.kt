package dev.argraur.moretech.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.argraur.moretech.database.MORETechDatabase

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : MORETechDatabase =
        Room.databaseBuilder(context, MORETechDatabase::class.java, "moretech-db")
            .fallbackToDestructiveMigration()
            .build()
}