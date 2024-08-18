package org.lotka.xenonx.presentation.screen.search

sealed class SearchEvent {

    data class SearchMessageChange(val searchMessage:String):SearchEvent()
}