package com.chplalex.taxi.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChooseTaxiViewModelFactory(private val pickUpPoint: String, private val destinationPoint: String) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ChooseTaxiViewModel::class.java)) {
            ChooseTaxiViewModel(pickUpPoint, destinationPoint) as T
        } else {
            throw IllegalArgumentException("Unknown class name")
        }
    }
}