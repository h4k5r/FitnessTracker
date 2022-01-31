package io.dev00.fitnesstracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.dev00.fitnesstracker.utils.fetchCurrentDate
import java.time.Year

@Entity(tableName = "steps")
data class Steps(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "steps")
    var steps: Int,

    @ColumnInfo(name = "day")
    var day: String,

    @ColumnInfo(name = "month")
    var month: String,

    @ColumnInfo(name = "year")
    var year: String
) {
    constructor() : this(
        id = null,
        steps = 0,
        day = fetchCurrentDate().split("/")[0],
        month = fetchCurrentDate().split("/")[1],
        year = fetchCurrentDate().split("/")[2]
    )

    constructor(steps: Int, day: String, month: String, year: String) : this(
        id = null,
        steps = steps,
        day = day,
        month = month,
        year = year
    )
}
