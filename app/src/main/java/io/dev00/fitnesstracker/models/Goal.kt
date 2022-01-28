package io.dev00.fitnesstracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey(autoGenerate = true)
    val id:Int?,

    @ColumnInfo(name = "goal_name")
    var goalName: String,
    @ColumnInfo(name = "goal_steps")
    var steps:Int,
    @ColumnInfo(name = "is_active")
    var isActive:Boolean = false
) {
    constructor() : this(id=null,goalName = "", steps = 0, isActive = false)
    constructor(goalName: String,steps: Int,isActive: Boolean) : this(id=null,goalName = goalName, steps = steps, isActive = isActive)
    constructor(goalName: String,steps: Int) : this(id=null,goalName = goalName, steps = steps, isActive = false)
}

