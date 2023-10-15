package dev.argraur.moretech.offices.model.office

data class OfficeOpenHours(
    val day: DayOfTheWeek,
    val open: Int,
    val close: Int
)