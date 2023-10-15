package dev.argraur.moretech.database.utils

import androidx.room.TypeConverter
import dev.argraur.moretech.database.model.atm.AtmServicesEmbed
import dev.argraur.moretech.database.model.office.OfficeOpenHoursEmbed
import dev.argraur.moretech.database.model.office.OfficeOpenHoursIndividualEmbed
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converter {
    @TypeConverter
    fun atmServicesListToJson(value: List<AtmServicesEmbed>): String = Json.encodeToString(value)

    @TypeConverter
    fun atmServicesJsonToList(value: String) = Json.decodeFromString<List<AtmServicesEmbed>>(value)

    @TypeConverter
    fun officeOpenHoursListToJson(value: List<OfficeOpenHoursEmbed>): String = Json.encodeToString(value)

    @TypeConverter
    fun officeOpenHoursJsonToList(value: String) = Json.decodeFromString<List<OfficeOpenHoursEmbed>>(value)

    @TypeConverter
    fun officeOpenHoursIndividualListToJson(value: List<OfficeOpenHoursIndividualEmbed>): String = Json.encodeToString(value)

    @TypeConverter
    fun officeOpenHoursIndividualJsonToList(value: String) = Json.decodeFromString<List<OfficeOpenHoursIndividualEmbed>>(value)
}