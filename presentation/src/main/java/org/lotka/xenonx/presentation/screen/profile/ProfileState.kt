package org.lotka.xenonx.presentation.screen.profile

import org.lotka.xenonx.domain.model.ProfileResponse

data class ProfileState(
    val isLoading : Boolean = false ,
    val profile : ProfileResponse? = null,
)
