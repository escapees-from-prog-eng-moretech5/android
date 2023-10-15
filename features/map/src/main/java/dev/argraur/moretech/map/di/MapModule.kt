package dev.argraur.moretech.map.di

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import com.google.maps.GeoApiContext
import com.google.maps.model.LatLng
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.argraur.moretech.map.di.annotations.DataSet
import dev.argraur.moretech.map.R
import dev.argraur.moretech.map.di.annotations.ApplicationMetadata
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Module
@InstallIn(SingletonComponent::class)
class MapModule {
    @Provides
    @ApplicationMetadata
    fun provideApplicationMetadata(@ApplicationContext context: Context) : Bundle =
        context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA).metaData

    @Provides
    fun provideGeoApiContext(@ApplicationMetadata applicationMetadata: Bundle): GeoApiContext {
        val apiKey = applicationMetadata.getString("com.google.android.geo.API_KEY")
        return GeoApiContext.Builder().apiKey(apiKey).build()
    }

    @Provides
    @DataSet
    fun provideDataSet(@ApplicationContext context: Context): List<@JvmSuppressWildcards LatLng> {
        val stream = context.resources.openRawResource(R.raw.offices)
        val text = stream.reader().readText()
        val json = Json.parseToJsonElement(text)
        return json.jsonArray
            .map { it.jsonObject.filter { e -> e.key == "latitude" || e.key == "longitude" }.mapValues { e -> e.value.jsonPrimitive.content.toDouble() } }
            .map { LatLng(it["latitude"]!!, it["longitude"]!!) }
    }
}