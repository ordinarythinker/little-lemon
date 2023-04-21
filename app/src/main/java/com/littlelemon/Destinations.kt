package com.littlelemon

interface Destination {
    val route : String
}

object Home : Destination {
    override val route: String = "Home"
}

object OnBoarding : Destination {
    override val route: String = "OnBoarding"
}

object Profile : Destination {
    override val route: String = "Profile"
}