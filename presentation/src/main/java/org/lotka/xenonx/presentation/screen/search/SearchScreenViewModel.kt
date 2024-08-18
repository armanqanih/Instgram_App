package org.lotka.xenonx.presentation.screen.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(

):ViewModel() {


    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()


    fun onEvent(event : SearchEvent){
        when(event){
            is SearchEvent.SearchMessageChange -> {
                _state.value = state.value.copy(
                    searchMessage = event.searchMessage
                )
            }
        }
    }



}