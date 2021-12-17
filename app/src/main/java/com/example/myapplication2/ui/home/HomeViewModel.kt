package com.example.myapplication2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.R

import android.widget.ArrayAdapter

import android.os.Bundle




class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Your parcels will be listed in here"
    }
    val text: LiveData<String> = _text



}