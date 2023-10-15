package dev.argraur.moretech.network.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.argraur.moretech.network.token.TokenStorage

@Module
@InstallIn(SingletonComponent::class)
class TokenModule {
    @Provides
    fun provideTokenStorage(@ApplicationContext context: Context) = TokenStorage(context)
}