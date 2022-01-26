package io.dev00.fitnesstracker.navigation

enum class FitnessTrackerScreens {
    HomeScreen,
    GoalsScreen,
    HistoryScreen;
//    companion object {
//        fun fromRoute(route:String?):FitnessTrackerScreens =
//            when(route?.substringBefore("/")) {
//                HomeScreen.name -> HomeScreen
//                GoalsScreen.name -> GoalsScreen
//                HistoryScreen.name -> HistoryScreen
//                null -> HomeScreen
//                else -> throw  IllegalArgumentException()
//            }
//    }
}