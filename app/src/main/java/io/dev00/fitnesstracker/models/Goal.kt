package io.dev00.fitnesstracker.models

data class Goal(
    var goalName: String,
    var steps:Int,
    var isActive:Boolean = false
) {
    constructor() : this(goalName = "", steps = 0, isActive = false)
}

