package org.lotka.xenonx.presentation.screen.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel@Inject constructor(

):ViewModel() {

    private val _toolbarState = MutableStateFlow(ProfileToolbarState())
     val toolbarState = _toolbarState.asStateFlow()

    fun setToolbarOffsetY(offset : Float){
       _toolbarState.value = _toolbarState.value.copy(
           toolbarOffsetY = offset
       )
    }

    fun setExpandedRatio(offset : Float){
        _toolbarState.value = _toolbarState.value.copy(
            expandedRatio = offset
        )
    }




}