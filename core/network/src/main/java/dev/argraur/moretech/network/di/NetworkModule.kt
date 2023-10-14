package dev.argraur.moretech.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.argraur.moretech.network.di.annotations.AuthInterceptorOkHttpClient
import dev.argraur.moretech.network.di.annotations.NoInterceptorOkHttpClient
import dev.argraur.moretech.network.retrofit.AuthService
import dev.argraur.moretech.network.utils.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit

private const val BASE_URL = "https://localhost"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @AuthInterceptorOkHttpClient
    fun provideAuthInterceptorOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @NoInterceptorOkHttpClient
    fun provideNoInterceptorOkHttpClient(): OkHttpClient =
        OkHttpClient()

    @Provides
    fun provideAuthService(
        @NoInterceptorOkHttpClient okHttpClient: OkHttpClient
    ): AuthService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callFactory(okHttpClient)
            .build()
            .create(AuthService::class.java)
    }
}