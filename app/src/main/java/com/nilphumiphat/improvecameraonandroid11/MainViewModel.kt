package com.nilphumiphat.improvecameraonandroid11

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData

class MainViewModel : ViewModel() {
    private val _text1: MutableLiveData<String> = MutableLiveData<String>()
    val text1: LiveData<String> get() = _text1

    init {
        _text1.value = "Success 55555"
    }

}