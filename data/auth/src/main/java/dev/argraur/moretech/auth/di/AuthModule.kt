package dev.argraur.moretech.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.argraur.moretech.auth.repository.AuthRepository
import dev.argraur.moretech.auth.repository.OfflineFirstAuthRepository
import dev.argraur.moretech.database.dao.AuthDao
import dev.argraur.moretech.network.AuthNetworkDataSource
import dev.argraur.moretech.network.token.TokenStorage

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Provides
    fun provideAuthRepository(tokenStorage: TokenStorage, authDao: AuthDao, authNetworkDataSource: AuthNetworkDataSource) : AuthRepository =
        OfflineFirstAuthRepository(tokenStorage, authDao, authNetworkDataSource)
}