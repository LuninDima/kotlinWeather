package ru.moondi.kotlinweather.utils

private val translations = mapOf(
    "clear" to "Ясно",
    "partly-cloudy" to "Малооблачно",
    "cloudy" to "Облачно с прояснениями",
    "overcast" to "Пасмурно",
    "drizzle" to "Морось",
    "light-rain" to "Небольшой дождь",
    "rain" to "Дождь",
    "moderate-rain" to "Умеренно сильный дождь",
    "heavy-rain" to "Сильный дождь",
    "continuous-heavy-rain" to "Длительный сильный дождь",
    "showers" to "Ливень",
    "wet-snow" to "Дождь со снегом",
    "light-snow" to "Небольшой снег",
    "snow" to "Снег",
    "snow-showers" to "Снегопад",
    "hail" to "Град",
    "thunderstorm" to "Гроза",
    "thunderstorm-with-rain" to "Дождь с грозой",
    "thunderstorm-with-hail" to "Гроза с градом",
    "night" to "Ночь",
    "morning" to "Утро",
    "day" to "День",
    "evening" to "вечер"
)

fun translation(condition: String):String{
    var translation = translations.get(condition)
    return  translation!!
}