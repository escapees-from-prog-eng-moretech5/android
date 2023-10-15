package dev.argraur.moretech.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.argraur.moretech.network.di.annotations.AuthInterceptorOkHttpClient
import dev.argraur.moretech.network.di.annotations.NoInterceptorOkHttpClient
import dev.argraur.moretech.network.retrofit.AtmService
import dev.argraur.moretech.network.retrofit.AuthService
import dev.argraur.moretech.network.retrofit.OfficeService
import dev.argraur.moretech.network.utils.AuthInterceptor
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.MediaType
import retrofit2.Retrofit

private const val BASE_URL = "http://10.0.2.2:8080"

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
    fun provideAuthService(): AuthService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
            .build()
            .create(AuthService::class.java)
    }

    @Provides
    fun provideOfficeService(
        @AuthInterceptorOkHttpClient okHttpClient: OkHttpClient
    ): OfficeService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callFactory(okHttpClient)
            .addConverterFactory(Json { coerceInputValues = true }.asConverterFactory(MediaType.parse("application/json")!!))
            .build()
            .create(OfficeService::class.java)
    }

    @Provides
    fun provideAtmService(
        @AuthInterceptorOkHttpClient okHttpClient: OkHttpClient
    ): AtmService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callFactory(okHttpClient)
            .addConverterFactory(Json { coerceInputValues = true }.asConverterFactory(MediaType.parse("application/json")!!))
            .build()
            .create(AtmService::class.java)
    }
}