package io.dev00.fitnesstracker.models

data class Goal(
    var goalName: String,
    var steps:Int,
    var isActive:Boolean = false
)
