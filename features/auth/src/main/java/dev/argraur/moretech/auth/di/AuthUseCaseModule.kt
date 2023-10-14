package dev.argraur.moretech.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.argraur.moretech.auth.domain.LoginUseCase
import dev.argraur.moretech.auth.domain.RegisterUseCase
import dev.argraur.moretech.auth.repository.AuthRepository

@Module
@InstallIn(SingletonComponent::class)
class AuthUseCaseModule {
    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase
        = LoginUseCase(authRepository)

    @Provides
    fun provideRegisterUseCase(authRepository: AuthRepository): RegisterUseCase
            = RegisterUseCase(authRepository)
}