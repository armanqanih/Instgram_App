package org.lotka.xenonx.presentation.screen.edit_profile

import org.lotka.xenonx.presentation.util.state.StandardTextFieldState

data class EditProfileState(
    val userNameState : StandardTextFieldState = StandardTextFieldState(),
    val githubTextState : StandardTextFieldState = StandardTextFieldState(),
    val instagramTextState : StandardTextFieldState = StandardTextFieldState(),
    val linkedInTextState : StandardTextFieldState = StandardTextFieldState(),
    val bioState : StandardTextFieldState = StandardTextFieldState(),



)
