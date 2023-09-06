package com.example.clase19mapsmvvm.view.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clase19mapsmvvm.model.TerremotoService
import com.example.clase19mapsmvvm.model.data.Terremoto
import kotlinx.coroutines.launch

class ListViewModel: ViewModel() {
    val terremotoService = TerremotoService()

    val status = MutableLiveData<Status>()
    val _status: LiveData<Status>
        get() = status

    val listTerremoto = MutableLiveData<MutableList<Terremoto>>()
    val _listTerromoto: LiveData<MutableList<Terremoto>>
        get() = listTerremoto

    init {
        viewModelScope.launch {
            runCatching {
                status.value = Status.LOADING
                terremotoService.getTerremoto()
            }.onSuccess { terremotos ->
                listTerremoto.value = terremotos
                status.value = Status.SUCCESS
            }.onFailure {
                status.value = Status.FAILED
            }
        }
    }
}

enum class Status {
    SUCCESS,
    FAILED,
    LOADING
}