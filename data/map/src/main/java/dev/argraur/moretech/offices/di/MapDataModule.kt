package dev.argraur.moretech.offices.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.argraur.moretech.database.dao.MapDao
import dev.argraur.moretech.network.AtmNetworkDataSource
import dev.argraur.moretech.network.OfficeNetworkDataSource
import dev.argraur.moretech.offices.repository.MapRepository

@Module
@InstallIn(SingletonComponent::class)
class MapDataModule {
    @Provides
    fun provideMapRepository(
        atmNetworkDataSource: AtmNetworkDataSource,
        officeNetworkDataSource: OfficeNetworkDataSource,
        mapDao: MapDao
    ) = MapRepository(atmNetworkDataSource, officeNetworkDataSource, mapDao)
}