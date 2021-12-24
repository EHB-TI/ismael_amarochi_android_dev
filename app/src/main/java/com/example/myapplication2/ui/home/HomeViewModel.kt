package com.example.myapplication2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import android.content.res.Resources

import android.widget.ArrayAdapter

import android.os.Bundle




class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Your parcels are listed in here"

    }
    val text: LiveData<String> = _text



}