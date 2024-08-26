package org.lotka.xenonx.presentation.util

sealed class UiEvent {

    data class ShowSnakeBar(val message : String) : UiEvent()
    data class  Navigate(val route : String) : UiEvent()



}