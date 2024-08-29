package org.lotka.xenonx.presentation.screen.search

import org.lotka.xenonx.domain.model.UserModel

data class SearchState(
    val searchMessage : String = "",
    val users : List<UserModel> = emptyList(),


    )
