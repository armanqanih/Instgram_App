package org.lotka.xenonx.presentation.screen.profile


import org.lotka.xenonx.domain.model.UserModel

data class ProfileState(
    val isLoading : Boolean = false ,
    val profile : UserModel? = null,

)
