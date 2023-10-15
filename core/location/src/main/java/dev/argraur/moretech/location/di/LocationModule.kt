package dev.argraur.moretech.location.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.argraur.moretech.activitycore.ActivityProvider
import dev.argraur.moretech.location.LocationProvider
import dev.argraur.moretech.location.di.annotations.FakeProvider
import dev.argraur.moretech.location.impl.FakeLocationProvider
import dev.argraur.moretech.location.impl.RealLocationProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocationModule {
    @Provides
    @Singleton
    fun provideLocationProvider(activityProvider: ActivityProvider): LocationProvider
        = RealLocationProvider(activityProvider)

    @Provides
    @FakeProvider
    fun provideFakeLocationProvider(): LocationProvider
        = FakeLocationProvider()
}