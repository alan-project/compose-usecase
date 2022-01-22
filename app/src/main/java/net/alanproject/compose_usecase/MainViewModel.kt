package net.alanproject.compose_usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _textFieldState: MutableLiveData<List<String>> = MutableLiveData()
    val textFieldState: LiveData<List<String>>
        get() = _textFieldState

    private val names = mutableListOf("ALAN", "ELIN", "JOYEL")


    fun fetchData() {
        _textFieldState.value = names
    }
}