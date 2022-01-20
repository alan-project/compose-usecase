package net.alanproject.compose_usecase.ui

import net.alanproject.compose_usecase.R

data class UserProfile constructor(val name: String, val status: Boolean, val drawableId: Int)

val userProfileList = arrayListOf(
    UserProfile(name = "Alan Kim", status = true, R.drawable.profile_picture),
    UserProfile(name = "Elin Cho", status = false, R.drawable.profile_picture2),
    UserProfile(name = "Joyel Kim", status = true, R.drawable.profile_picture3),
    UserProfile(name = "David Smith ", status = false, R.drawable.profile_picture4),
    UserProfile(name = "Maria Hernandez", status = true, R.drawable.profile_picture5),
    UserProfile(name = "Robert Smith", status = false, R.drawable.profile_picture6)
)