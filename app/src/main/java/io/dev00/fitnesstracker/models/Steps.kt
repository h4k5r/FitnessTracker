package io.dev00.fitnesstracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "steps")
data class Steps(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "steps")
    var steps: Int,

    @ColumnInfo(name = "date")
    val date: String
) {
    constructor():this(id = null, steps = 0, date = "")
    constructor(steps: Int,date: String):this(id = null, steps = steps, date = date)
}
