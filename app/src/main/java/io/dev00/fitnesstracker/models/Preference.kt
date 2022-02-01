package io.dev00.fitnesstracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "preference")
data class Preference (
    @PrimaryKey(autoGenerate = true)
    val id:Int?,

    @ColumnInfo(name = "name")
    val name:String,

    @ColumnInfo(name = "value")
    var value: Boolean
    ) {
    constructor(name:String,value: Boolean):this(id = 0,name = name,value = value)
}

