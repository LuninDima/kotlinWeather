package ru.moondi.kotlinweather.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val City: String,
    val temperature: Int,
    val condition: String
)
