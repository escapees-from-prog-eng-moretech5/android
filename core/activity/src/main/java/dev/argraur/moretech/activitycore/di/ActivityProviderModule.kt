package dev.argraur.moretech.activitycore.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.argraur.moretech.activitycore.ActivityProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ActivityProviderModule {
    @Provides
    @Singleton
    fun provideActivityProvider() = ActivityProvider()
}