package dev.argraur.moretech.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.argraur.moretech.database.MORETechDatabase
import dev.argraur.moretech.database.dao.AuthDao
import dev.argraur.moretech.database.dao.MapDao

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {
    @Provides
    fun provideAuthDao(database: MORETechDatabase) : AuthDao =
        database.authDao()

    @Provides
    fun provideMapDao(database: MORETechDatabase): MapDao =
        database.mapDao()
}