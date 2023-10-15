package dev.argraur.moretech.offices.model.office

data class OfficeOpenHoursIndividual(
    val day: DayOfTheWeek,
    val open: Int,
    val close: Int
)