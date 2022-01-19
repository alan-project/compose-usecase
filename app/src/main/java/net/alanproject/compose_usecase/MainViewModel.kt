package net.alanproject.compose_usecase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {
    val textFieldState = MutableLiveData(listOf(""))
    val names = mutableListOf("ALAN","ELIN","JOYEL")


    fun fetchData() {
        textFieldState.value = names
    }
}