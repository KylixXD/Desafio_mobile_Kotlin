package com.example.desafio_mesas_comandas.viewmodel

import TableRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.desafio_mesas_comandas.data.local.TableEntity
import com.example.desafio_mesas_comandas.utils.ReadJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TableRepository(application)

    private val _mesas = MutableStateFlow<List<TableEntity>>(emptyList())
    val mesas: StateFlow<List<TableEntity>> = _mesas.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _selectedFilterName = MutableStateFlow("Visão Geral")
    val selectedFilterName: StateFlow<String> = _selectedFilterName.asStateFlow()

    private val _queryFilter = MutableStateFlow<String?>(null)

    val tables: Flow<PagingData<TableEntity>> = combine(
        _searchText,
        _queryFilter
    ) { text, filter ->
        Pair(text.ifBlank { null }, filter)
    }.flatMapLatest { (text, filter) ->
        repository.getPaginatedTables(searchText = text, activityType = filter)
    }.cachedIn(viewModelScope)



    init {
        loadTables()
//        applyFilters()
    }

    fun loadTables() {
        viewModelScope.launch {
            repository.tableCount().let { count ->
                if (count == 0) {
                    _isLoading.value = true
                    withContext(Dispatchers.IO) {
                        val context = getApplication<Application>()
                        val mockTables: List<TableEntity> =
                            ReadJson.readJsonMock(context, "Mock.json")
                        repository.upsertAll(mockTables)
                    }
                    _isLoading.value = false
                }
            }
        }
    }


    fun updateSearch(text: String) {
        _searchText.value = text
//        applyFilters()
    }

    fun updateFilter(filterName: String) {
        _selectedFilterName.value = filterName

        _queryFilter.value = when (filterName) {
            "Visão Geral" -> null
            "Em Atendimento" -> "active"
            "Ociosas" -> "inactive"
            "Disponíveis" -> "empty"
            "Sem Pedidos" -> "waiting"
            else -> null
        }
//        applyFilters()
    }

//    private fun applyFilters() {
//        viewModelScope.launch {
//            val searchTextQuery = _searchText.value.ifBlank { null }
//            val activityTypeQuery = _queryFilter.value
//
//            repository.getFilteredTables(
//                searchText = searchTextQuery,
//                activityType = activityTypeQuery
//            ).collect { filtered ->
//                _mesas.value = filtered
//            }
//        }
//
//    }
}