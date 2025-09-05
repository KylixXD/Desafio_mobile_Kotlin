package com.example.desafio_mesas_comandas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio_mesas_comandas.data.model.Checkpad
import com.example.desafio_mesas_comandas.data.model.CheckpadApiResponse
import com.example.desafio_mesas_comandas.utils.ReadJson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapViewModel(application: Application) : AndroidViewModel(application) {
    private val _mesas = MutableStateFlow<CheckpadApiResponse?>(null)
    val mesas: StateFlow<CheckpadApiResponse?> = _mesas

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    //    private val _selectedFilter = MutableStateFlow<>
    private val _selectedFilter = MutableStateFlow("Visão Geral")
    val selectedFilter: StateFlow<String> = _selectedFilter


    init {
        loadMock()
    }

    fun loadMock() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = ReadJson.readJsonMock(getApplication(), "Mock.json")
                _mesas.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateSearch(text: String) {
        _searchText.value = text
    }

    fun updateFilter(filter: String) {
        _selectedFilter.value = filter
    }

}